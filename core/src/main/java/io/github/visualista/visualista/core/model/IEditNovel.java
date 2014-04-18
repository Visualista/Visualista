package io.github.visualista.visualista.core.model;

import java.util.List;

public interface IEditNovel {

	public String getName();
	
	public void setName(String name);
	
	public int getSceneCount();
	
	public void addScene(Scene scene);
	
	public List<Scene> getScenes();
}
