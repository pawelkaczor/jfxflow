package com.zenjava.jfxflow.actvity;

import javafx.beans.property.ReadOnlyObjectProperty;

public interface HasTransition
{
    ReadOnlyObjectProperty<Transition> currentTransitionProperty();

    Transition getCurrentTransition();
}
