package io.github.visualista.visualista.core.model;

public class SceneFactory {
    private IdGenerator id = new IdGenerator();

    public Scene createScene() {

        return new Scene(id.generateId());
    }

}
