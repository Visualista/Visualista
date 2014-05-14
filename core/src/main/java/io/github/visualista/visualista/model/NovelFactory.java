package io.github.visualista.visualista.model;

import java.util.*;

public class NovelFactory {

    private IdGenerator idGenerator;
    private SceneFactory sceneFactory;
    private Random random;

    public NovelFactory(SceneFactory sceneFactory) {
        this.idGenerator = new IdGenerator();
        this.sceneFactory = sceneFactory;
        this.random = new Random();
    }

    public Novel createNovel() {
        Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(new ArrayList<Scene>());
        novel.addScene(scene);
        novel.setName(""+random.nextInt(1000));
        return novel;
    }
}
