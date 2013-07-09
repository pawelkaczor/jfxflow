package com.zenjava.jfxflow.transition;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.DialogActivity;
import com.zenjava.jfxflow.actvity.ParentActivity;
import com.zenjava.jfxflow.actvity.Transition;
import com.zenjava.jfxflow.dialog.Dialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class DialogFactory implements TransitionFactory {

    private ParentActivity parentActivity;
    private String stylesheet;
    private boolean headerVisible = true;

    public DialogFactory(ParentActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

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
                    String title = null;
                    if (toActivity instanceof DialogActivity) {
                        title = ((DialogActivity) toActivity).getTitle();
                    }
                    Dialog dialog = new Dialog(title, stylesheet, headerVisible);
                    Parent content = (Parent) toActivity.getView().toNode();
                    dialog.setContent(content);
                    dialog.show(contentArea);
                    dialog.showingProperty().addListener(new ChangeListener<Boolean>() {
                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                            if (!newValue) {
                                parentActivity.resetCurrentPlace();
                            }
                        }
                    });
                }

            }
        };
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public void setHeaderVisible(boolean headerVisible) {
        this.headerVisible = headerVisible;
    }
}
