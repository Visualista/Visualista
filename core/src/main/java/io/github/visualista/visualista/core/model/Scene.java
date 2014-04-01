package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final int id;
    private final Grid grid;
    private final List<Integer> actorsInScene;

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
}
