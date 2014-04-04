package io.github.visualista.visualista.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.github.visualista.visualista.core.LibgdxSample;

public class VisualistaDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1900;
		config.height = 1000;
		new LwjglApplication(new LibgdxSample(), config);
	}
}
