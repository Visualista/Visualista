package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.*;

public class Scene implements Nameable, IGetScene {
    private final Grid grid;

    private final List<Actor> actorsInScene;
    private String name;
    private String storyText;

    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene(Grid grid, List<Actor> actorsInScene) {
        this.grid = grid;
        this.actorsInScene = new ArrayList<Actor>(actorsInScene);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return "Scene [grid=" + grid + ", actorsInScene=" + actorsInScene
                + ", name=" + name + "]";
    }

    public void addActor(Actor actor) {
        actorsInScene.add(actor);

    }

    public List<Actor> getActorsInScene() {
        return new ArrayList<Actor>(actorsInScene);
    }
    
    public void removeActor(Actor actor){
        actorsInScene.remove(actor);
    }

}
