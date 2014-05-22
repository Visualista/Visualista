package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.*;

public class Scene implements Nameable, IGetScene {
    private final Grid grid;

    private final List<Actor> actorsInScene;
    private String name;
    private String storyText = "";

    private Image image;

    public Scene(Grid grid, List<Actor> actorsInScene) {
        this.grid = grid;
        this.actorsInScene = new ArrayList<Actor>(actorsInScene);
    }

    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(String storyText) {
        if(storyText==null){
            storyText = "";
        }
        this.storyText = storyText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<IGetActor> getActorsInScene() {
        return new ArrayList<IGetActor>(actorsInScene);
    }

    public void removeActor(Actor actor){
        actorsInScene.remove(actor);
    }

    public void setImage(Image image) {
        this.image = image;
    }
    @Override
    public Image getImage (){
        return image;
    }

    @Override
    public IGetGrid getIGetGrid() {
        return grid;
    }


}
