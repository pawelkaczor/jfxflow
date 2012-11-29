package com.zenjava.jfxflow.actvity;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public interface ParentView<NodeType extends Node> extends View<NodeType>
{
    Pane getChildArea();
}
