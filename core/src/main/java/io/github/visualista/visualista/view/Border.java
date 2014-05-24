package io.github.visualista.visualista.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Border extends Group {

    private Actor containedActor;
    private final BorderActor borderActor;
    private boolean lineOutsideActor;

    public Border() {
        this(Color.BLACK);
    }

    public Border(final Color borderColor) {
        borderActor = new BorderActor(borderColor);
        super.addActor(borderActor);
        setTouchable(Touchable.childrenOnly);
        borderActor.setTouchable(Touchable.disabled);
    }

    @Override
    public void setColor(final Color color) {
        borderActor.setColor(color);
        super.setColor(color);
    }

    @Override
    public void setColor(final float r, final float g, final float b, final float a) {
        borderActor.setColor(r, g, b, a);
        super.setColor(r, g, b, a);
    }

    public void setActor(final Actor actor) {
        if (actor == this) {
            throw new IllegalArgumentException("actor cannot be the Border.");
        }
        super.removeActor(containedActor);
        containedActor = actor;
        if (actor != null) {
            if (lineOutsideActor) {
                actor.setBounds(borderActor.getLineSize(),
                        borderActor.getLineSize(), getWidth()
                        - borderActor.getLineSize() * 2,
                        getHeight() - borderActor.getLineSize() * 2);
            } else {
                actor.setBounds(0, 0, getWidth(), getHeight());
            }
            super.addActorBefore(borderActor, actor);
        }

    }

    @Override
    public void clearChildren() {
        setActor(null);

    }

    @Override
    public void setWidth(final float width) {
        if (containedActor != null) {
            if (lineOutsideActor) {
                containedActor.setWidth(width - borderActor.getLineSize() * 2);
            } else {
                containedActor.setWidth(width);
            }
        }
        borderActor.setWidth(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(final float height) {
        if (containedActor != null) {
            if (lineOutsideActor) {
                containedActor.setHeight(height - borderActor.getLineSize() * 2);
            } else {
                containedActor.setHeight(height);
            }
        }
        borderActor.setHeight(height);
        super.setHeight(height);
    }

    @Override
    public void setSize(final float width, final float height) {
        if (containedActor != null) {
            if (lineOutsideActor) {
                containedActor.setSize(width - borderActor.getLineSize() * 2, height
                        - borderActor.getLineSize() * 2);
            } else {
                containedActor.setSize(width, height);
            }
        }
        borderActor.setSize(width, height);
        super.setSize(width, height);
    }

    @Override
    public void setBounds(final float x, final float y, final float width, final float height) {
        if (containedActor != null) {
            if (lineOutsideActor) {
                containedActor.setSize(width - borderActor.getLineSize() * 2, height
                        - borderActor.getLineSize() * 2);
            } else {
                containedActor.setSize(width, height);
            }
        }
        borderActor.setSize(width, height);
        super.setBounds(x, y, width, height);
    }

    public Actor getActor() {
        return containedActor;
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    @Deprecated
    @Override
    public void addActor(final Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    @Deprecated
    @Override
    public void addActorAt(final int index, final Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    @Deprecated
    @Override
    public void addActorBefore(final Actor actorBefore, final Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    /**
     * @deprecated Border may have only a single child.
     * @see #setActor(Actor)
     */
    @Deprecated
    @Override
    public void addActorAfter(final Actor actorAfter, final Actor actor) {
        throw new UnsupportedOperationException("Use Border#setActor.");
    }

    @Override
    public boolean removeActor(final Actor actor) {
        if (actor != containedActor) {
            return false;
        }
        setActor(null);
        return true;
    }

    public int getLineSize() {
        return borderActor.getLineSize();
    }

    public void setLineSize(final int lineSize) {
        borderActor.setLineSize(lineSize);
        if (containedActor != null) {
            containedActor.setSize(getWidth() - lineSize, getHeight() - lineSize);
        }
    }

    public void setLineOutsideActor(final boolean lineShouldBeOutsideActor) {
        lineOutsideActor = lineShouldBeOutsideActor;
        if (containedActor != null) {
            if (lineShouldBeOutsideActor) {
                containedActor.setSize(getWidth() - borderActor.getLineSize(),
                        getHeight() - borderActor.getLineSize());
            } else {
                containedActor.setSize(getWidth(), getHeight());
            }
        }
    }
}
