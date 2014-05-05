package io.github.visualista.visualista.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

import io.github.visualista.view.VisualistaView;

public class VisualistaDesktop {
	static {
		GdxNativesLoader.load();
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1200;
		config.height = 700;
		LwjglApplication temp = new LwjglApplication(new VisualistaView(),
				config);

	}
}
