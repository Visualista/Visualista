package io.github.visualista.visualista.java;

import io.github.visualista.visualista.core.VisualistaPlayer;
import io.github.visualista.visualista.playercontroller.PlayerController;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.view.PlayerView;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public enum PlayerViewLauncher {
    ;

    public static void launch(final LwjglApplicationConfiguration config) {
        final PlayerView view = new PlayerView(new Dimension(config.width,
                config.height));
        final VisualistaPlayer model = new VisualistaPlayer();
        new PlayerController(model, view, new DesktopFilePicker());
        new LwjglApplication(view, config);
    }

}
