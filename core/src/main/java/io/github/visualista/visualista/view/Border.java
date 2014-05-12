package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public class Border extends Group {

    private Actor actor;
    private BorderActor borderActor;

    public Border() {
        this(Color.BLACK);
    }

    public Border(Color borderColor) {
        borderActor = new BorderActor(borderColor);
        super.addActor(borderActor);
        setTouchable(Touchable.childrenOnly);
        borderActor.setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
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

    public void setActor(Actor actor) {
        if (actor == this)
            throw new IllegalArgumentException("actor cannot be the Border.");
        super.removeActor(this.actor);
        this.actor = actor;
        if (actor != null) {
            actor.setBounds(0, 0, this.getWidth(), this.getHeight());
            super.addActorBefore(borderActor,actor);
        }

    }

    @Override
    public void setWidth(float width) {
        if (actor != null) {
            actor.setWidth(width);
        }
        borderActor.setWidth(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        if (actor != null) {
            actor.setHeight(height);
        }
        borderActor.setHeight(height);
        super.setHeight(height);
    }

    @Override
    public void setSize(float width, float height) {
        if (actor != null) {
            actor.setSize(width, height);
        }
        borderActor.setSize(width, height);
        super.setSize(width, height);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (actor != null) {
            actor.setBounds(actor.getX(), actor.getY(), width, height);
        }
        borderActor.setBounds(borderActor.getX(), borderActor.getY(), width,
                height);
        super.setBounds(x, y, width, height);
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

}
