package io.github.visualista.visualista.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

import io.github.visualista.visualista.core.VisualistaEditor;
import io.github.visualista.visualista.editorcontroller.EditorController;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.EditorView;

public class VisualistaDesktop {
    private static final int APPLICATION_HEIGHT = 700;
    private static final int APPLICATION_WIDTH = 1200;

    static {
        GdxNativesLoader.load();
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = APPLICATION_WIDTH;
        config.height = APPLICATION_HEIGHT;
        final EditorView view = new EditorView(new Dimension(config.width,config.height),new DesktopFilePicker());
        final VisualistaEditor model = new VisualistaEditor();
        new EditorController(model, view);
        new LwjglApplication(view, config);
    }
}
