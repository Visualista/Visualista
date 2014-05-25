package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;
import io.github.visualista.visualista.util.Point;

import java.util.List;

public class Novel implements Nameable, IGetNovel {
    private Scene currentScene;
    private String name;
    List<Scene> scenes;

    public Novel(final List<Scene> scenes) {
        setName("NewNovel");
        this.scenes = scenes;
    }

    public void addScene(final Scene scene) {
        scenes.add(scene);

    }

    public Actor getActorAt(final Point position) {
        return currentScene.getGrid().getAt(position)
                .getActor();
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSceneCount() {
        return scenes.size();
    }

    @Override
    public List<Scene> getScenes() {
        return scenes;
    }

    public void setCurrentScene(final Scene newCurrentScene) {
        currentScene = newCurrentScene;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

}
