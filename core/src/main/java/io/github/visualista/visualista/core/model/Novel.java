package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;
import io.github.visualista.visualista.util.ReferenceManager;

import java.util.*;

public class Novel implements Identifiable{
	private ReferenceManager<Novel> referenceManager;
    private final List<Integer> sceneReferences;

	private final int id;
    private String name;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Novel(int id, List<Integer> sceneReferences) {
    	this.id = id;
        this.sceneReferences = sceneReferences;
    }

    public int getSceneCount() {
        return sceneReferences.size();
    }

    public void setReferenceManager(ReferenceManager<Novel> referenceManager){
    	this.referenceManager = referenceManager;
    }
    
	@Override
	public int getId() {
		return id;
	}

}
