package io.github.visualista.visualista.html;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.EditorView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class VisualistaHtml extends GwtApplication {
    private static final int APPLICATION_HEIGHT = 320;
    private static final int APPLICATION_WIDTH = 480;

    @Override
    public ApplicationListener getApplicationListener() {
        return new EditorView(new Dimension(APPLICATION_WIDTH,APPLICATION_HEIGHT),null);
    }

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(APPLICATION_WIDTH,
                APPLICATION_HEIGHT);
    }
}
