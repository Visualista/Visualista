package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;

import java.util.*;

public class Novel implements Identifiable{
	private final Integer id;
	List<Scene> scenes;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Novel(Integer id, List<Scene> scenes) {
        this.id = id;
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

	@Override
	public Integer getId() {
		return id;
	}

}
