package io.github.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BorderScrollPane extends ScrollPane {

    private Texture blackTexture;
    private Texture whiteTexture;

    public BorderScrollPane(Actor widget, ScrollPaneStyle style) {
        super(widget, style);
        setUp();
    }

    public BorderScrollPane(Actor widget, Skin skin, String styleName) {
        super(widget, skin, styleName);
        setUp();
    }

    public BorderScrollPane(Actor widget, Skin skin) {
        super(widget, skin);
        setUp();
    }

    public BorderScrollPane(Actor widget) {
        super(widget);
        setUp();
    }

    private void setUp() {
        blackTexture = createTexture(Color.BLACK);
        whiteTexture = createTexture(Color.WHITE);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width - 3, height - 3);
    }

    @Override
    public float getWidth() {
        return super.getWidth() + 3;
    }

    @Override
    public float getRight() {
        return super.getRight() + 3;
    }

    @Override
    public void setX(float x) {
        super.setX(x + 2);
    }

    @Override
    public void setY(float y) {
        super.setY(y + 2);
    }

    @Override
    public float getX() {
        return super.getX() - 2;
    }

    @Override
    public float getY() {
        return super.getY() - 2;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(blackTexture, this.getX() - 2, this.getY() - 2,
                this.getWidth() + 3, this.getHeight() + 3);
        batch.draw(whiteTexture, this.getX() - 1, this.getY() - 1,
                this.getWidth() - 2, this.getHeight() - 2);
        super.draw(batch, parentAlpha);

    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x + 2, y + 2);
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
