package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Identifiable;
import io.github.visualista.visualista.util.ReferenceManager;

import java.util.*;

public class Novel implements Identifiable {
    //private ReferenceManager<Scene> sceneReferenceManager;
    //private final Set<Integer> sceneReferences;
	List<Scene> scenes;


    private final int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Novel(int id, Set<Integer> sceneReferences,
            Set<Integer> actorReferences) {
        this.id = id;
        //this.sceneReferences = sceneReferences;
        //this.actorReferences = actorReferences;
    }

    public int getSceneCount() {
        return scenes.size();
    }


/*
    public void setSceneReferenceManager(
            ReferenceManager<Scene> sceneReferenceManager) {
        this.sceneReferenceManager = sceneReferenceManager;
    }*/
/*
    public void setActorReferenceManager(
            ReferenceManager<Actor> actorReferenceManager) {
        this.actorReferenceManager = actorReferenceManager;
    }*/

    @Override
    public int getId() {
        return id;
    }

    public void addScene(Scene scene) {
    	//sceneReferenceManager.addObject(scene);
        //sceneReferences.add(scene.getId());
    	scenes.add(scene);

    }

	public List<Scene> getScenes() {
		return scenes;
	}

    /*
    public Scene getSceneById(Integer id) {
        return sceneReferenceManager.getObject(id);
    }

    public Set<Integer> getSceneReferences() {
        return sceneReferences;
    }
    public void addActor(Actor actor) {
        actorReferenceManager.addObject(actor);
    }

    public Actor getActorById(Integer id) {
        return actorReferenceManager.getObject(id);
    }

    public Set<Integer> getActorReferences() {
        return actorReferences;
    }*/

}
