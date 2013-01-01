package com.zenjava.jfxflow.transition;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public class EmptyTransitionFactory implements TransitionFactory {
    public Transition createTransition(Pane contentArea, Activity fromActivity, Activity toActivity) {
        return new Transition() {
            public EventHandler<ActionEvent> getOnFinished() {
                return null;
            }

            public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
                //do nothing
            }

            public void execute() {
                //do nothing
            }
        };
    }
}
