package com.zenjava.jfxflow.transition;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.Transition;
import com.zenjava.jfxflow.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public class DialogFactory implements TransitionFactory {
    public Transition createTransition(final Pane contentArea, final Activity fromActivity, final Activity toActivity) {
        return new Transition() {
            public EventHandler<ActionEvent> getOnFinished() {
                return null;
            }

            public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
                // do nothing
            }

            public void execute() {
                if (toActivity != null) {
                    Dialog dialog = new Dialog();
                    dialog.setContent(toActivity.getView().toNode());
                    dialog.show(contentArea);
                }

            }
        };
    }
}
