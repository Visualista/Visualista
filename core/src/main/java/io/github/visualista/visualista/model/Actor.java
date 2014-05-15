package io.github.visualista.visualista.model;

import io.github.visualista.visualista.editorcontroller.ActorEventSource;
import io.github.visualista.visualista.util.Nameable;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

public class Actor extends ActorEventSource implements Nameable, IGetActor,
        IPlayActor {

    public static final Actor EMPTY_ACTOR = new Actor();
    private Image image;
    private final List<IAction> actions;
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    public Actor() {
        actions = new ArrayList<IAction>();
        image = new Image(null);
    }

    public List<IAction> getActions() {
        return actions;
    }

    public void addAction(IAction action) {
        actions.add(action);
    }

    public void removeAction(IAction action) {
        actions.remove(action);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Actor [Actions=" + actions + ", Name=" + name + "]";
    }

    @Override
    public void playActor() {
        if (!this.equals(EMPTY_ACTOR)) {
            fireActorEvent();
        }

    }

}
