package com.zenjava.jfxflow.actvity;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FxAnimation implements Transition {

    private Animation animation;

    public FxAnimation(Animation animation) {
        this.animation = animation;
    }

    public EventHandler<ActionEvent> getOnFinished() {
        return animation.getOnFinished();
    }

    public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
        animation.setOnFinished(eventHandler);
    }

    public void execute() {
        animation.play();
    }

    public Animation getAnimation() {
        return animation;
    }
}
