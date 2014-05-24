package io.github.visualista.visualista.model;

import java.util.ArrayList;
import java.util.Random;

public class NovelFactory {

    private static final int MAX_RANDOM_NUMBER = 1000;
    private final SceneFactory sceneFactory;
    private final Random random;

    public NovelFactory(final SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
        random = new Random();
    }

    public Novel createNovel() {
        Scene scene = sceneFactory.createScene();
        Novel novel = new Novel(new ArrayList<Scene>());
        novel.addScene(scene);
        novel.setName(""+random.nextInt(MAX_RANDOM_NUMBER));
        return novel;
    }
}
