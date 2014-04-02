package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Scene implements Identifiable{
    private final int id;
    private final Grid grid;
    
    private final List<Integer> actorsInScene;
    private String name;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scene(int id, Grid grid) {
        this.id = id;
        this.grid = grid;
        actorsInScene = new ArrayList<Integer>();
    }

    public int getId() {
        return id;
    }
    public Grid getGrid() {
        return grid;
    }

	@Override
	public String toString() {
		return "Scene [id=" + id + ", actorsInScene=" + actorsInScene
				+ ", name=" + name + "]";
	}


}
