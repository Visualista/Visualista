package io.github.visualista.visualista.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class NovelFactoryTest {

    private NovelFactory factory;

    @Before
    public void setUp() throws Exception {
        GridFactory gridFactory = new GridFactory();
        SceneFactory sceneFactory = new SceneFactory(gridFactory);
        factory = new NovelFactory(sceneFactory);
    }

    @Test
    public void testCreateNovel() {
        Novel novel = factory.createNovel();
        assertNotNull(novel);
        assertEquals(1, novel.getSceneCount());
        assertNotNull(novel.getScenes().get(0));
    }

}
