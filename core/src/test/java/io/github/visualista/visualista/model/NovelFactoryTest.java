package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.Scene;

import org.junit.Test;

public class NovelFactoryTest {

	@Test
	public void testCreateNovel() {
	    NovelFactory factory = new NovelFactory();
	    Novel novel = factory.createNovel();
		assertNotNull(novel);
		assertEquals(1,novel.getSceneCount());
		assertNotNull(novel.getSceneById(1));
		Scene scene = novel.getSceneById(1);
		assertEquals(1,scene.getId());
	}

}
