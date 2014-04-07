package io.github.visualista.visualista.core.model;

import java.util.*;

public class NovelFactory {

    private SceneFactory sceneFactory;

    public NovelFactory(SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
    }

    public Novel createNovel() {
    	Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(new ArrayList<Scene>());
        novel.addScene(scene);
        return novel;
    }
}
