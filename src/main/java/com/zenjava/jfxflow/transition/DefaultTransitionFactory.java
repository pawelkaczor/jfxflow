package com.zenjava.jfxflow.transition;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.FxAnimation;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DefaultTransitionFactory implements TransitionFactory
{
    public com.zenjava.jfxflow.actvity.Transition createTransition(final Pane container,
                                      final Activity fromActivity,
                                      Activity toActivity)
    {
        Transition transition = createMainTransition(fromActivity, toActivity);

        ViewTransition exit = null;
        if (fromActivity != null)
        {
            if (fromActivity instanceof HasExitTransition)
            {
                exit = ((HasExitTransition) fromActivity).getExitTransition();
            }
            else
            {
                exit = getDefaultExitTransition(fromActivity.getView().toNode());
            }
            exit.setupBeforeAnimation(container.getBoundsInParent());
            addAnimation(transition, exit.getAnimation());
        }

        ViewTransition entry = null;
        if (toActivity != null)
        {
            if (toActivity instanceof HasEntryTransition)
            {
                entry = ((HasEntryTransition) toActivity).getEntryTransition();
            }
            else
            {
                entry = getDefaultEntryTransition(toActivity.getView().toNode());
            }
            entry.setupBeforeAnimation(container.getBoundsInParent());
            addAnimation(transition, entry.getAnimation());
            addView(container, toActivity.getView().toNode());
        }

        final ViewTransition finalExit = exit;
        final ViewTransition finalEntry = entry;
        transition.setOnFinished(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                if (fromActivity != null)
                {
                    removeView(container, fromActivity.getView().toNode());
                }

                if (finalEntry != null)
                {
                    finalEntry.cleanupAfterAnimation();
                }
                if (finalExit != null)
                {
                    finalExit.cleanupAfterAnimation();
                }
            }
        });

        return new FxAnimation(transition);
    }

    protected void addView(Pane container, Node node) {
        container.getChildren().add(node);
    }

    protected void removeView(Pane container, Node node) {
        container.getChildren().remove(node);
    }

    protected Transition createMainTransition(Activity fromActivity, Activity toActivity)
    {
        if (toActivity instanceof HasEntryTransition)
        {
            if ( !((HasEntryTransition)toActivity).isSequentialTransition())
            {
                return new ParallelTransition();
            }
        }
        return new SequentialTransition();
    }

    protected ViewTransition getDefaultEntryTransition(Node node)
    {
        return new FadeInTransition(node, Duration.millis(300));
    }

    protected ViewTransition getDefaultExitTransition(Node node)
    {
        return new FadeOutTransition(node, Duration.millis(300));
    }

    protected void addAnimation(Transition transition, Animation child)
    {
        if (transition instanceof SequentialTransition)
        {
            ((SequentialTransition) transition).getChildren().add(child);
        }
        else if (transition instanceof ParallelTransition)
        {
            ((ParallelTransition) transition).getChildren().add(child);
        }
        else
        {
            throw new IllegalArgumentException("Main transition must be either a Parallel or Sequential Transition");
        }
    }
}
