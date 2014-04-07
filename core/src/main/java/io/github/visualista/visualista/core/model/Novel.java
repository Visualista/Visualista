package io.github.visualista.visualista.core.model;

import java.util.*;

public class Novel{
	List<Scene> scenes;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Novel(List<Scene> scenes) {
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

}
