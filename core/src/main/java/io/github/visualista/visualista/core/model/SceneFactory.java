package io.github.visualista.visualista.core.model;

import java.util.ArrayList;

public class SceneFactory {
    private IdGenerator id = new IdGenerator();
    private GridFactory gridFactory;
    
    public SceneFactory(GridFactory gridFactory){
        this.gridFactory = gridFactory;
    }

    public Scene createScene() {
    	Grid grid = gridFactory.createGrid();
    	
        return new Scene(id.generateId(),grid, new ArrayList<Actor>());
    }
}
