package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.*;

public class Scene implements Nameable{
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
        this.actorsInScene = actorsInScene;
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
		return actorsInScene;
	}

}
