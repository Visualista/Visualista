package io.github.visualista.visualista.core.model;

import java.util.*;

public class Novel {
    private final Map<Integer,Scene> sceneMap;
    private final int id;
    private String name;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Novel(int id, Map<Integer,Scene> sceneMap) {
    	this.id = id;
        this.sceneMap = sceneMap;
    }

    public int getSceneCount() {
        return sceneMap.size();
    }

    public Scene getSceneById(Integer i) {
        return sceneMap.get(i);
    }

}
