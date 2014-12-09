package com.zenjava.jfxflow.navigation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Place
{
    private StringProperty name;
    private Map<String, Object> parameters;
    private Locale locale = Locale.getDefault();
    private boolean cacheable = true;

    public Place(String name)
    {
        this(name, null);
    }

    public Place(String name, Map<String, Object> parameters)
    {
        this.name = new SimpleStringProperty(name);
        this.parameters = new HashMap<String, Object>();
        if (parameters != null)
        {
            this.parameters.putAll(parameters);
        }
    }

    public StringProperty nameProperty()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public Map<String, Object> getParameters()
    {
        return parameters;
    }

    public String toString()
    {
        return String.format("Place[%s]", getName());
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
