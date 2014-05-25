package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;
import io.github.visualista.visualista.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Scene implements Nameable, IGetScene {
    private final List<Actor> actorsInScene;

    private final Grid grid;
    private Image image;
    private String name;

    private String storyText = "";

    public Scene(final Grid grid, final List<Actor> actorsInScene) {
        this.grid = grid;
        this.actorsInScene = new ArrayList<Actor>(actorsInScene);
    }

    public void addActor(final Actor actor) {
        actorsInScene.add(actor);

    }

    public Tile getTileAt(final Point point) {
        return grid.getAt(point);
    }

    @Override
    public List<IGetActor> getActorsInScene() {
        return new ArrayList<IGetActor>(actorsInScene);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public IGetGrid getIGetGrid() {
        return grid;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStoryText() {
        return storyText;
    }

    public void removeActor(final Actor actor) {
        actorsInScene.remove(actor);
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    public void setStoryText(final String storyText) {
        if (storyText == null) {
            this.storyText = "";
            return;
        }
        this.storyText = storyText;
    }

    @Override
    public String toString() {
        return name + " (" + grid.getSize() + ")";
    }

}
