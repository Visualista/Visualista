package io.github.visualista.visualista.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Rectangle extends Actor {
    private Texture borderTexture;

    public Rectangle(Color borderColor) {
        borderTexture = createTexture(borderColor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        batch.draw(borderTexture, x, y, width, height);

    }

    @Override
    public void setColor(Color color) {
        borderTexture = createTexture(color);
        super.setColor(color);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        this.setColor(new Color(r,g,b,a));
        super.setColor(r, g, b, a);
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
