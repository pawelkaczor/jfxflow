package com.zenjava.jfxflow.actvity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface Transition {

    EventHandler<ActionEvent> getOnFinished();

    void setOnFinished(EventHandler<ActionEvent> eventHandler);

    void execute();
}
