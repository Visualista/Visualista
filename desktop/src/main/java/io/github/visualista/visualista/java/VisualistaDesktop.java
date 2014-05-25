package io.github.visualista.visualista.java;

import java.util.Scanner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

public enum VisualistaDesktop {
    ;
    private static final int APPLICATION_HEIGHT = 700;
    private static final int APPLICATION_WIDTH = 1200;

    static {
        GdxNativesLoader.load();
    }

    public static void main(final String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = APPLICATION_WIDTH;
        config.height = APPLICATION_HEIGHT;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write 1 for Player and 2 for Editor. Anything else will exit");
        String in = scanner.next();
        if ("1".equals(in)) {
            PlayerViewLauncher.launch(config);
        } else if ("2".equals(in)) {
            EditorViewLauncher.launch(config);
        }
        scanner.close();
    }

}
