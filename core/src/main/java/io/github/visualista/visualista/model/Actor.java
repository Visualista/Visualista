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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        if (name == null) {
            this.name = "";
            return;
        } else {
            this.name = name;
        }
    }

    @Override
    public List<IAction> getActions() {
        return actions;
    }

    public void addAction(final IAction action) {
        actions.add(action);
    }

    public void removeAction(final IAction action) {
        actions.remove(action);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }

}
