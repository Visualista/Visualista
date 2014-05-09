package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public class Border extends Group {
    private static final int LINE_SIZE = 3;

    private Texture borderTexture;
    private Texture innerTexture;

    private Actor actor;

    public Border() {
        setUp(Color.WHITE, Color.BLACK);
    }

    public Border(Color innerColor, Color borderColor) {
        setUp(innerColor, borderColor);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        float x;
        float y;
        float width;
        float height;
        if (actor == null) {
            x = this.getX();
            y = this.getY();
            width = this.getWidth();
            height = this.getHeight();
        } else {
            x = actor.getX() - LINE_SIZE;
            y = actor.getY() - LINE_SIZE;
            width = actor.getWidth() + LINE_SIZE * 2;
            height = actor.getHeight() + LINE_SIZE * 2;
        }
        batch.draw(borderTexture, x, y, width, height);
        batch.draw(innerTexture, x + LINE_SIZE, y + LINE_SIZE, width
                - LINE_SIZE * 2, height - LINE_SIZE * 2);
        super.draw(batch, parentAlpha);
        if (actor != null) {
            actor.draw(batch, parentAlpha);

        }
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

    public void setActor(Actor actor) {
        if (actor == this)
            throw new IllegalArgumentException("actor cannot be the Border.");
        super.removeActor(this.actor);
        this.actor = actor;
        super.addActor(actor);

    }

    public Actor getActor() {
        return actor;
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    public void addActorAfter(Actor actorAfter, Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    public boolean removeActor(Actor actor) {
        if (actor != this.actor)
            return false;
        setActor(null);
        return true;
    }

    @Override
    public float getX() {
        if (actor != null) {
            return actor.getX() - LINE_SIZE;
        }
        return super.getX();
    }

    @Override
    public float getY() {
        if (actor != null) {
            return actor.getY() - LINE_SIZE;
        }
        return super.getY();
    }

    @Override
    public float getWidth() {
        if (actor != null) {
            return actor.getWidth() + LINE_SIZE * 2;
        }
        return super.getWidth();
    }

    @Override
    public float getHeight() {
        if (actor != null) {
            return actor.getHeight() + LINE_SIZE * 2;
        }
        return super.getHeight();
    }

}
