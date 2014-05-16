package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Border extends Group {

    private Actor actor;
    private BorderActor borderActor;
    private boolean lineOutsideActor;

    public Border() {
        this(Color.BLACK);
    }

    public Border(Color borderColor) {
        borderActor = new BorderActor(borderColor);
        super.addActor(borderActor);
        setTouchable(Touchable.childrenOnly);
        borderActor.setTouchable(Touchable.disabled);
    }


    public void setActor(Actor actor) {
        if (actor == this)
            throw new IllegalArgumentException("actor cannot be the Border.");
        super.removeActor(this.actor);
        this.actor = actor;
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setBounds(borderActor.getLineSize(),
                        borderActor.getLineSize(), this.getWidth()
                                - borderActor.getLineSize() * 2,
                        this.getHeight() - borderActor.getLineSize() * 2);
            } else {
                actor.setBounds(0, 0, this.getWidth(), this.getHeight());
            }
            super.addActorBefore(borderActor, actor);
        }

    }

    @Override
    public void setWidth(float width) {
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setWidth(width - borderActor.getLineSize() * 2);
            } else {
                actor.setWidth(width);
            }
        }
        borderActor.setWidth(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setHeight(height - borderActor.getLineSize() * 2);
            } else {
                actor.setHeight(height);
            }
        }
        borderActor.setHeight(height);
        super.setHeight(height);
    }

    @Override
    public void setSize(float width, float height) {
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setSize(width - borderActor.getLineSize() * 2, height
                        - borderActor.getLineSize() * 2);
            } else {
                actor.setSize(width, height);
            }
        }
        borderActor.setSize(width, height);
        super.setSize(width, height);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setSize(width - borderActor.getLineSize() * 2, height
                        - borderActor.getLineSize() * 2);
            } else {
                actor.setSize(width, height);
            }
        }
        borderActor.setSize(width, height);
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

    public int getLineSize() {
        return borderActor.getLineSize();
    }

    public void setLineSize(int lineSize) {
        borderActor.setLineSize(lineSize);
        if (actor != null) {
            actor.setSize(getWidth() - lineSize, getHeight() - lineSize);
        }
    }

    public void setLineOutsideActor(boolean lineShouldBeOutsideActor) {
        this.lineOutsideActor = lineShouldBeOutsideActor;
        if (actor != null) {
            if (lineShouldBeOutsideActor) {
                actor.setSize(getWidth() - borderActor.getLineSize(),
                        getHeight() - borderActor.getLineSize());
            } else {
                actor.setSize(getWidth(), getHeight());
            }
        }
    }
}
