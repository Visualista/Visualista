package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BorderActor extends Actor {
    @Override
    public void setColor(Color color) {
        borderTexture = createTexture(color);
        super.setColor(color);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        setColor(new Color(r,g,b,a));
        super.setColor(r, g, b, a);
    }

    private Texture borderTexture;
    private int lineSize = 3;

    public BorderActor(Color borderColor) {
        borderTexture = createTexture(borderColor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        if (width > getLineSize() * 2 && height > getLineSize() * 2) {
            batch.draw(borderTexture, x, y, width, getLineSize());
            batch.draw(borderTexture, x, y + height - getLineSize(), width,
                    getLineSize());
            batch.draw(borderTexture, x, y + getLineSize(), getLineSize(),
                    height - getLineSize() * 2);
            batch.draw(borderTexture, x + width - getLineSize(), y
                    + getLineSize(), getLineSize(), height - getLineSize()
                    * 2);
        } else {
            batch.draw(borderTexture, x, y, width, height);
        }
    }


    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
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