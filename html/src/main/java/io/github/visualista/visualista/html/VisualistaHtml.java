package io.github.visualista.visualista.html;

import io.github.visualista.visualista.core.Visualista;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class VisualistaHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Visualista();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
