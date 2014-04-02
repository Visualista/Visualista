package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;
import io.github.visualista.visualista.util.ReferenceManager;

import java.util.*;

public class Novel implements Identifiable{
	private ReferenceManager<Scene> referenceManager;
    private final Set<Integer> sceneReferences;

	private final int id;
    private String name;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Novel(int id, Set<Integer> sceneReferences) {
    	this.id = id;
        this.sceneReferences = sceneReferences;
    }

    public int getSceneCount() {
        return sceneReferences.size();
    }

    public void setReferenceManager(ReferenceManager<Scene> referenceManager){
    	this.referenceManager = referenceManager;
    }
    
	@Override
	public int getId() {
		return id;
	}

	public void addScene(Scene scene) {
		referenceManager.addObject(scene);
		sceneReferences.add(scene.getId());
		
	}

	public Scene getSceneById(Integer id) {
		return referenceManager.getObject(id);
	}
	
	public Set<Integer> getSceneReferences(){
		return sceneReferences;
	}

}
