package io.github.visualista.visualista.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

import io.github.visualista.visualista.core.LibgdxSample;
import io.github.visualista.visualista.core.TestGame;
import io.github.visualista.visualista.core.VisualistaEditor;

public class VisualistaDesktop {
	static {
	    GdxNativesLoader.load();
	}
	
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1100;
		config.height = 700;
		//new LwjglApplication(new LibgdxSample(), config);
		new LwjglApplication(new VisualistaEditor(new DesktopFilePicker()), config);
	}
}
