package com.zenjava.jfxflow.transition;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.Transition;
import javafx.scene.layout.Pane;

public interface TransitionFactory
{
    Transition createTransition(Pane contentArea, Activity fromActivity, Activity toActivity);
}
