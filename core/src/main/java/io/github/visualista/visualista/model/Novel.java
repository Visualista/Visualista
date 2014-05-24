package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.List;

public class Novel implements Nameable, IGetNovel {
    List<Scene> scenes;
    private String name;
    private Scene currentScene;

    public Novel(final List<Scene> scenes) {
        setName("NewNovel");
        this.scenes = scenes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int getSceneCount() {
        return scenes.size();
    }

    public void addScene(final Scene scene) {
        scenes.add(scene);

    }

    @Override
    public List<Scene> getScenes() {
        return scenes;
    }

    public void setCurrentScene(final Scene newCurrentScene) {
        currentScene = newCurrentScene;
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

}
