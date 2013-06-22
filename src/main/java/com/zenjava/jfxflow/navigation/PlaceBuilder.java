package com.zenjava.jfxflow.navigation;

import javafx.util.Builder;

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

    public PlaceBuilder name(String name)
    {
        place.setName(name);
        return this;
    }

    public PlaceBuilder cacheable(boolean cacheable) {
        place.setCacheable(cacheable);
        return this;
    }

    public PlaceBuilder parameter(String name, Object value)
    {
        this.place.getParameters().put(name, value);
        return this;
    }

    public Place build()
    {
        return place;
    }
}
