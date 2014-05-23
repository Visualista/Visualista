package io.github.visualista.visualista.java;

import java.util.Scanner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

import io.github.visualista.visualista.core.VisualistaEditor;
import io.github.visualista.visualista.core.VisualistaPlayer;
import io.github.visualista.visualista.editorcontroller.EditorController;
import io.github.visualista.visualista.playercontroller.PlayerController;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.EditorView;
import io.github.visualista.visualista.view.PlayerView;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write 1 for Player and 2 for Editor. Anything else will exit");
        String in = scanner.next();
        if ("1".equals(in)) {
            final PlayerView view = new PlayerView(new Dimension(config.width,
                    config.height));
            final VisualistaPlayer model = new VisualistaPlayer();
            new PlayerController(model, view, new DesktopFilePicker());
            new LwjglApplication(view, config);
        } else if ("2".equals(in)) {
            final EditorView view = new EditorView(new Dimension(config.width,
                    config.height), new DesktopFilePicker());
            final VisualistaEditor model = new VisualistaEditor();
            new EditorController(model, view);
            new LwjglApplication(view, config);
        }

    }
}
