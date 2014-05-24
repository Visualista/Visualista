package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Rectangle extends Actor {
    private Texture borderTexture;

    public Rectangle(final Color borderColor) {
        borderTexture = createTexture(borderColor);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        batch.draw(borderTexture, x, y, width, height);

    }

    @Override
    public void setColor(final Color color) {
        borderTexture = createTexture(color);
        super.setColor(color);
    }

    @Override
    public void setColor(final float r, final float g, final float b, final float a) {
        this.setColor(new Color(r,g,b,a));
        super.setColor(r, g, b, a);
    }

    private static Texture createTexture(final Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA4444);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
