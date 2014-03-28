package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;

import org.junit.Test;

public class NovelFactoryTest {

	@Test
	public void testCreateNovel() {
		assertTrue(NovelFactory.createNovel() instanceof Novel);
	}

}
