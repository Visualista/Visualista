package io.github.visualista.visualista.core.model;

import java.util.*;

public class NovelFactory {

    private IdGenerator id = new IdGenerator();

    private SceneFactory sceneFactory;

    public NovelFactory(SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
    }

    public Novel createNovel() {
        Map<Integer, Scene> sceneMap = new HashMap<Integer, Scene>();
        Scene scene = sceneFactory.createScene();
        sceneMap.put(scene.getId(), scene);
        return new Novel(sceneMap);
    }
}
