package io.github.visualista.visualista.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BorderActor extends Actor {
    private Texture borderTexture;
    private int lineSize = 3;

    private boolean log = false;

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        Actor current = this;
        if (log) {
            float worldY = 0;
            while (current != null) {
                worldY += current.getY();
                current = current.getParent();
            }
            Gdx.app.log("worldY ", worldY + "");
        }
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

    public void makeLog() {
        log = true;

    }

    public BorderActor(Color borderColor) {
        borderTexture = createTexture(borderColor);
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