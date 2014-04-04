package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.core.model.SceneFactory;

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
		assertEquals(1,novel.getSceneCount());
		assertNotNull(novel.getScenes().get(0));
		Scene scene = novel.getScenes().get(0);
		assertEquals(1,scene.getId());
	}

}
