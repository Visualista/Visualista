package io.github.visualista.visualista.model;

import java.util.*;

public class NovelFactory {

    private IdGenerator idGenerator;
    private SceneFactory sceneFactory;

    public NovelFactory(SceneFactory sceneFactory) {
        this.idGenerator = new IdGenerator();
        this.sceneFactory = sceneFactory;
    }

    public Novel createNovel() {
        Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(new ArrayList<Scene>());
        novel.addScene(scene);
        return novel;
    }
}
