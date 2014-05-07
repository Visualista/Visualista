package io.github.visualista.visualista.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import io.github.visualista.view.VisualistaView;

public class VisualistaHtml extends GwtApplication {
    private static final int APPLICATION_HEIGHT = 320;
    private static final int APPICATION_WIDTH = 480;

    @Override
    public ApplicationListener getApplicationListener() {
        return new VisualistaView();
    }

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(APPICATION_WIDTH,
                APPLICATION_HEIGHT);
    }
}
