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
        Map<Integer, Scene> sceneMap = new HashMap<Integer, Scene>();
        Scene scene = sceneFactory.createScene();
        sceneMap.put(scene.getId(), scene);
        return new Novel(id, sceneMap);
    }
}
