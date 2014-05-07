package io.github.visualista.visualista.controller;

import io.github.visualista.view.VisualistaView;
import io.github.visualista.visualista.core.Visualista;


public class EditorController {

	private Visualista visualista;
	private VisualistaView view;

	public EditorController(final Visualista visualista,
			final VisualistaView view) {
		this.visualista = visualista;
		this.view = view;
	}

}
