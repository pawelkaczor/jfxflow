package com.zenjava.jfxflow.actvity;

import com.zenjava.jfxflow.util.FXUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractActivity<ViewType extends View>
        implements InjectedView<ViewType>, Activatable, HasWorkers, Releasable, Initializable
{
    private ViewType view;
    private final BooleanProperty active;
    private final BooleanProperty initialized;
    private final BooleanProperty released;
    private final ObservableList<Worker> workers;

    protected AbstractActivity()
    {
        this.active = new SimpleBooleanProperty();
        this.released = new SimpleBooleanProperty();
        this.initialized = new SimpleBooleanProperty(false);
        this.active.addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    activated();
                }
                else
                {
                    deactivated();
                }
            }
        });

        this.workers = FXCollections.observableArrayList();
    }

    public ViewType getView()
    {
        return view;
    }

    public void setView(ViewType view)
    {
        this.view = view;
    }

    public void setActive(boolean active)
    {
        this.active.set(active);
    }

    public boolean isActive()
    {
        return this.active.get();
    }

    public BooleanProperty activeProperty()
    {
        return this.active;
    }

    public BooleanProperty initializedProperty()
    {
        return this.initialized;
    }

    public void setInitialized(boolean initialized)
    {
        this.initialized.set(initialized);
    }

    public boolean isInitialized()
    {
        return this.initialized.get();
    }

    public void release()
    {
        setActive(false);
        released.set(true);
    }

    public ReadOnlyBooleanProperty releasedProperty()
    {
        return released;
    }

    public boolean isReleased()
    {
        return released.get();
    }

    public ObservableList<Worker> getWorkers()
    {
        return workers;
    }

    protected void activated()
    {
    }

    protected void deactivated()
    {
    }

    protected <T> T getChild(String id) {
        return FXUtils.getChildByID((Parent) getView().toNode(), id);
    }

    protected void executeTask(Task task)
    {
        watchWorker(task);
        new Thread(task).start();
    }

    protected void watchWorker(final Worker worker)
    {
        worker.runningProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean olValue, Boolean newValue)
            {
                if (newValue)
                {
                    workers.add(worker);
                }
                else
                {
                    workers.remove(worker);
                }
            }
        });
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
        setInitialized(true);
    }

    protected void initialize() {
        // do nothing
    }

}
