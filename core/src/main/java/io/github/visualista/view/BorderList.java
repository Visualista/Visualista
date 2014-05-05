package io.github.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BorderList extends List {
	private Texture blackTexture;
	private Texture whiteTexture;

	public BorderList(Object[] items, Skin skin) {
		super(items, skin);
		setUp();
	}

	public BorderList(Object[] items, ListStyle style) {
		super(items, style);
		setUp();
	}

	public BorderList(Object[] items, Skin skin, String styleName) {
		super(items, skin, styleName);
		setUp();
	}

	private void setUp() {
		blackTexture = createTexture(Color.BLACK);
		whiteTexture = createTexture(Color.WHITE);
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width - 2, height - 2);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(blackTexture, this.getX() - 2, this.getY() - 2,
				this.getWidth(), this.getHeight());
		batch.draw(whiteTexture, this.getX() - 1, this.getY() - 1,
				this.getWidth() - 2, this.getHeight() - 2);
		super.draw(batch, parentAlpha);

	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x + 1, y + 1);
	}

	private static Texture createTexture(Color color) {
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA4444);
		pixmap.setColor(color);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

}
