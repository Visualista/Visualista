package io.github.visualista.visualista.core.model;

public class GridFactory {

    private static final int GRID_DEFAULT_HEIGHT = 5;
    private static final int GRID_DEFAULT_WIDTH = 5;

    public GridFactory() {

    }

    public Grid createGrid() {
        return new Grid(GRID_DEFAULT_WIDTH, GRID_DEFAULT_HEIGHT);
    }

}
