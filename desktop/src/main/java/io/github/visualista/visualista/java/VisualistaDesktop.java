package io.github.visualista.visualista.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

import io.github.visualista.visualista.controller.EditorController;
import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.VisualistaView;

public class VisualistaDesktop {
    private static final int APPLICATION_HEIGHT = 700;
    private static final int APPLICATION_WIDTH = 1200;

    static {
        GdxNativesLoader.load();
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.width = APPLICATION_WIDTH;
        config.height = APPLICATION_HEIGHT;
        final VisualistaView view = new VisualistaView(new Dimension(config.width,config.height));
        final Visualista model = new Visualista();
        new EditorController(model, view);
        new LwjglApplication(view, config);
    }
}
