package com.zenjava.jfxflow.actvity;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FxmlLoader {
    private Callback<Class<?>, Object> controllerFactory;
    private final Map<URL, Activity> activities = new HashMap<URL, Activity>();
    private BuilderFactory builderFactory = new JavaFXBuilderFactory();

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(URL fxmlFile, ResourceBundle resources)
            throws FxmlLoadException {
        if (!activities.containsKey(fxmlFile)) {
            reload(fxmlFile, resources);
        }
        return (Type) activities.get(fxmlFile);
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> void reload(URL fxmlFile, ResourceBundle resources)
            throws FxmlLoadException {
        FXMLLoader loader = new FXMLLoader(fxmlFile, resources, builderFactory, controllerFactory);
        Node rootNode;
        try {
            rootNode = (Node) loader.load();
        } catch (IOException e) {
            throw new FxmlLoadException("", e);
        }

        Type controller = (Type) loader.getController();
        if (controller instanceof InjectedView) {
            if (rootNode instanceof View) {
                ((InjectedView) controller).setView((View) rootNode);
            } else {
                if (controller instanceof ParentActivity) {
                    ((InjectedView) controller).setView(new DefaultParentView((Pane) rootNode));
                } else {
                    ((InjectedView) controller).setView(new SimpleView(rootNode));
                }
            }
        }
        registerActivity(fxmlFile, controller);
    }

    private <Type extends Activity> void registerActivity(URL fxmlFile, Type activity) {
        activities.put(fxmlFile, activity);
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void setBuilderFactory(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }
}
