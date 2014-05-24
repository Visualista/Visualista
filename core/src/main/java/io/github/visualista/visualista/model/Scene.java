package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.ArrayList;
import java.util.List;

public class Scene implements Nameable, IGetScene {
    private final Grid grid;

    private final List<Actor> actorsInScene;
    private String name;
    private String storyText = "";

    private Image image;

    public Scene(final Grid grid, final List<Actor> actorsInScene) {
        this.grid = grid;
        this.actorsInScene = new ArrayList<Actor>(actorsInScene);
    }

    @Override
    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(final String storyText) {
        if (storyText == null) {
            this.storyText = "";
            return;
        }
        this.storyText = storyText;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return name + " (" + grid.getSize() + ")";
    }

    public void addActor(final Actor actor) {
        actorsInScene.add(actor);

    }

    @Override
    public List<IGetActor> getActorsInScene() {
        return new ArrayList<IGetActor>(actorsInScene);
    }

    public void removeActor(final Actor actor) {
        actorsInScene.remove(actor);
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public IGetGrid getIGetGrid() {
        return grid;
    }

}
