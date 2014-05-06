package io.github.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class Border extends WidgetGroup {
	private static final int LINE_SIZE = 3;

	private Texture borderTexture;
	private Texture innerTexture;

	public Border() {
		setUp(Color.WHITE, Color.BLACK);
	}
	
	public Border(Color innerColor, Color borderColor){
		setUp(innerColor,borderColor);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(borderTexture, this.getX(), this.getY(), this.getWidth(),
				this.getHeight());
		batch.draw(innerTexture, this.getX() + LINE_SIZE, this.getY()
				+ LINE_SIZE, this.getWidth() - LINE_SIZE*2, this.getHeight()
				- LINE_SIZE*2);
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

	private void setUp(Color innerColor, Color borderColor) {
		borderTexture = createTexture(borderColor);
		innerTexture = createTexture(innerColor);
	}

}
