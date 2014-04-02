package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.ReferenceManager;

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
        Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(id, sceneReferences);
        novel.setReferenceManager(new ReferenceManager<Scene>());
        novel.addScene(scene.getId(), scene);
        //TODO Referene manager should be passed in
        return novel;
    }
}
