package com.zenjava.jfxflow.actvity;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FxmlLoader {
    private Callback<Class<?>, Object> controllerFactory;
    private BuilderFactory builderFactory = new JavaFXBuilderFactory();
    private ClassLoader classLoader;

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(URL fxmlFile, ResourceBundle resources)
            throws FxmlLoadException {
        FXMLLoader loader = new FXMLLoader(fxmlFile, resources, builderFactory, controllerFactory);
        if (classLoader != null) {
            loader.setClassLoader(classLoader);
        }
        Node rootNode;
        try {
            rootNode = (Node) loader.load();
        } catch (IOException e) {
            throw new FxmlLoadException("", e);
        }

        Type controller = loader.getController();
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
        return controller;
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void setBuilderFactory(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
