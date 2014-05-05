package io.github.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class Border extends WidgetGroup {
	private Actor actor;

	private Texture blackTexture;
	private Texture whiteTexture;

	public Border(Actor actor) {
		this.actor = actor;
		setUp();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(blackTexture, this.getX() - 2, this.getY() - 2,
				this.getWidth() + 3, this.getHeight() + 3);
		batch.draw(whiteTexture, this.getX() - 1, this.getY() - 1,
				this.getWidth() - 2, this.getHeight() - 2);
		super.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}

	private static Texture createTexture(Color color) {
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA4444);
		pixmap.setColor(color);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

	private void setUp() {
		blackTexture = createTexture(Color.BLACK);
		whiteTexture = createTexture(Color.WHITE);
	}

}
