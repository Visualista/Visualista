package io.github.visualista.visualista.core.model;

public class SceneFactory {
    private IdGenerator id = new IdGenerator();
    private GridFactory gridFactory;
    
    public SceneFactory(GridFactory gridFactory){
        this.gridFactory = gridFactory;
    }

    public Scene createScene() {

        return new Scene(id.generateId(),gridFactory.createGrid());
    }
}
