package io.github.visualista.visualista.controller;

import io.github.visualista.visualista.core.Visualista;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class EditorController implements EventListener {

	private Visualista visualista;

	@Override
	public boolean handle(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	public EditorController(Visualista visualista) {
		this.visualista = visualista;
	}

}
