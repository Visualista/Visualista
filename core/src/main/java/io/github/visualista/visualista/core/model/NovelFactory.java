package io.github.visualista.visualista.core.model;

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
    	List<Integer> sceneReferences = new ArrayList<Integer>();
        Scene scene = sceneFactory.createScene();
        sceneReferences.add(scene.getId());
        return new Novel(id, sceneReferences);
    }
}
