package io.github.visualista.visualista.java;

import io.github.visualista.visualista.core.VisualistaEditor;
import io.github.visualista.visualista.editorcontroller.EditorController;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.EditorView;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public enum EditorViewLauncher {
    ;

    public static void launch(final LwjglApplicationConfiguration config) {
        final EditorView view = new EditorView(new Dimension(config.width,
                config.height), new DesktopFilePicker());
        final VisualistaEditor model = new VisualistaEditor();
        new EditorController(model, view);
        new LwjglApplication(view, config);

    }
}
