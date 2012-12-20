package com.zenjava.jfxflow.util;

import javafx.scene.Parent;

public class WidgetNotFoundException extends RuntimeException {

    public WidgetNotFoundException(Parent parent, String id) {
        super(String.format("Widget '%s' not found in parent '%s'", id, parent.getId()));
    }
}
