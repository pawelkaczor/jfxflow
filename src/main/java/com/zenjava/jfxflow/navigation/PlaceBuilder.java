package com.zenjava.jfxflow.navigation;

import javafx.util.Builder;

import java.util.Locale;

public class PlaceBuilder implements Builder<Place>
{
    private Place place;

    public PlaceBuilder() {
        this(null);
    }

    public PlaceBuilder(String placeName) {
        this.place = new Place(placeName);
    }

    public static PlaceBuilder create() {
        return new PlaceBuilder();
    }

    public static Place place(String name) {
        return place(name, true);
    }

    public static Place place(String name, boolean cacheable) {
        return create().name(name).cacheable(cacheable).build();
    }

    public PlaceBuilder name(String name) {
        place.setName(name);
        return this;
    }

    public PlaceBuilder cacheable(boolean cacheable) {
        place.setCacheable(cacheable);
        return this;
    }

    public PlaceBuilder parameter(String name, Object value) {
        this.place.getParameters().put(name, value);
        return this;
    }

    public Place build()
    {
        return place;
    }

    public PlaceBuilder locale(Locale locale) {
        this.place.setLocale(locale);
        return this;
    }
}
