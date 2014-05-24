package io.github.visualista.visualista.model;

import java.util.ArrayList;

public class SceneFactory {
    private final GridFactory gridFactory;

    public SceneFactory(final GridFactory gridFactory) {
        this.gridFactory = gridFactory;
    }

    public Scene createScene() {
        Grid grid = gridFactory.createGrid();
        Scene newScene = new Scene(grid, new ArrayList<Actor>());
        newScene.setName("Unnamed");
        return newScene;
    }
}
