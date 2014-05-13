package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.*;

public class Novel implements Nameable, IGetNovel {
    List<Scene> scenes;
    private String name;
    private Scene currentScene;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Novel(List<Scene> scenes) {
        setName("NewNovel");
        this.scenes = scenes;
    }

    public int getSceneCount() {
        return scenes.size();
    }

    public void addScene(Scene scene) {
        scenes.add(scene);

    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setCurrentScene(Scene newCurrentScene){
        currentScene = newCurrentScene;
    }
    
    public Scene getCurrentScene() {
        return currentScene;
    }

}
