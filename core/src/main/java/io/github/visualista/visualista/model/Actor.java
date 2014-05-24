package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.ArrayList;
import java.util.List;


public class Actor implements Nameable, IGetActor{

    public static final Actor EMPTY_ACTOR = new Actor();
    private Image image;
    private final List<IAction> actions;
    private String name = "";

    public Actor() {
        actions = new ArrayList<IAction>();
        image = new Image(null);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (name == null) {
            this.name = "";
            return;
        } else {
            this.name = name;
        }
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
        return name;
    }

}
