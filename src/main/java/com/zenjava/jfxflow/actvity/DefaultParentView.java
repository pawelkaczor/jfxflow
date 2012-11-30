package com.zenjava.jfxflow.actvity;

import javafx.scene.layout.Pane;

public class DefaultParentView extends SimpleView<Pane> implements ParentView<Pane> {

    public DefaultParentView(Pane pane) {
        super(pane);
    }

    public Pane getChildArea() {
        return toNode();
    }
}
