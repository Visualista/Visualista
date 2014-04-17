package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.*;

public class Novel implements Nameable {
	List<Scene> scenes;
	private String name;

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

}
