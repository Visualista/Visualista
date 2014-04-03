package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.ReferenceManager;
import io.github.visualista.visualista.util.ReferenceManagerFactory;

import java.util.*;

public class NovelFactory {

    private final IdGenerator idGen;

    private SceneFactory sceneFactory;

    public NovelFactory(SceneFactory sceneFactory) {
    	idGen = new IdGenerator();
        this.sceneFactory = sceneFactory;
    }

    public Novel createNovel() {
    	int id = idGen.generateId();
    	Set<Integer> sceneReferences = new HashSet<Integer>();
    	Set<Integer> actorReferences = new HashSet<Integer>();
        Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(id, new ArrayList<Scene>());
        ReferenceManagerFactory<Scene> rfsFactory = new ReferenceManagerFactory<Scene>();
    	ReferenceManagerFactory<Actor> rfaFactory = new ReferenceManagerFactory<Actor>();
    	
    	//novel.setSceneReferenceManager(rfsFactory.createReferenceManager());
    	
        novel.addScene(scene);
        //novel.setActorReferenceManager(rfaFactory.createReferenceManager());
        //TODO Referene manager should be passed in
        return novel;
    }
}
