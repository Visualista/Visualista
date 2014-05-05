package io.github.visualista.visualista.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame extends Game implements GdxFileChooser.Callback {
	GdxFileChooser screen;

	public TestGame() {

	}

	@Override
	public void create() {

		this.screen = new GdxFileChooser(this);

		screen.create();
		screen.setFilter("", "");
		// screen.loadItems("");
		this.setScreen(screen);

	}

	@Override
	public void choose(String directory) {
		System.out.println("Choose: " + directory);

	}

	@Override
	public void cancel() {
		System.out.println("Cancel");

	}

}
