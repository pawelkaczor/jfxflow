package com.zenjava.jfxflow.navigation;

import com.zenjava.jfxflow.actvity.Activity;
import com.zenjava.jfxflow.actvity.FxmlLoader;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class SpringPlaceResolver extends AbstractPlaceResolver {

    private final ApplicationContext context;

    private FxmlLoader loader;

    public SpringPlaceResolver(final ApplicationContext context) {
        this.context = context;
        this.loader = new FxmlLoader(new Callback<Class<?>, Object>() {
            public Object call(Class<?> aClass) {
                return context.getBean(aClass);
            }
        });
    }

    @Override
    public Activity findActivity(Place place) {
        try {
            return loader.load(context.getResource(place.getName()).getURL(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
