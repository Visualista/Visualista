package io.github.visualista.visualista.core.model;

public class Scene {
    private final int id;
    private final Grid grid;

    public Scene(int id, Grid grid) {
        this.id = id;
        this.grid = grid;
    }

    public int getId() {
        return id;
    }
    public Grid getGrid() {
        return grid;
    }
}
