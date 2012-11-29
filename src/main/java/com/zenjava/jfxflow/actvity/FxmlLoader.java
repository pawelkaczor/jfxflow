package com.zenjava.jfxflow.actvity;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FxmlLoader
{
    private Callback<Class<?>, Object> controllerFactory = null;

    public FxmlLoader() {}

    public FxmlLoader(javafx.util.Callback<java.lang.Class<?>,java.lang.Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(String fxmlFile)
            throws FxmlLoadException
    {
        return (Type) load(fxmlFile, null, null);
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(String fxmlFile, String resourceBundle)
            throws FxmlLoadException
    {
        return (Type) load(fxmlFile, ResourceBundle.getBundle(resourceBundle), null);
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(String fxmlFile, ResourceBundle resourceBundle)
            throws FxmlLoadException
    {
        return (Type) load(fxmlFile, resourceBundle, null);
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(String fxmlFile, ResourceBundle resources, Map<String, Object> variables)
            throws FxmlLoadException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(fxmlFile);
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            if (controllerFactory != null) {
                loader.setControllerFactory(controllerFactory);
            }

            loader.setLocation(getClass().getResource(fxmlFile));

            if (resources != null)
            {
                loader.setResources(resources);
            }

            if (variables != null)
            {
                loader.getNamespace().putAll(variables);
            }

            Node rootNode = (Node) loader.load(fxmlStream);

            Type controller = (Type) loader.getController();
            if (controller instanceof InjectedView)
            {
                View view;
                if (rootNode instanceof View)
                {
                    ((InjectedView) controller).setView((View) rootNode);
                }
                else
                {
                    ((InjectedView) controller).setView(new SimpleView(rootNode));
                }
            }
            return controller;
        }
        catch (Exception e)
        {
            // map checked exception to a runtime exception - this is a system failure, not a business logic failure
            // so using checked exceptions for this is not necessary.
            throw new FxmlLoadException(String.format(
                    "Unable to load FXML from '%s': %s", fxmlFile, e.getMessage()), e);
        }
        finally
        {
            if (fxmlStream != null)
            {
                try
                {
                    fxmlStream.close();
                }
                catch (IOException e)
                {
                    System.err.println("WARNING: error closing FXML stream: " + e);
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <Type extends Activity> Type load(URL fxmlFile, ResourceBundle resources, Map<String, Object> variables)
            throws FxmlLoadException {
        FXMLLoader loader = new FXMLLoader(fxmlFile, resources, new JavaFXBuilderFactory(), controllerFactory);
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
                ((InjectedView) controller).setView(new SimpleView(rootNode));
            }
        }
        return controller;
    }

}
