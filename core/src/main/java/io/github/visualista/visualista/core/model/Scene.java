package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Scene implements Identifiable{
    private final int id;
    private final Grid grid;
    private final List<Integer> actorsInScene; //Note that actors are referenced by IDs

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
