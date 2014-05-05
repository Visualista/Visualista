package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class NovelTest {
	Novel novel;
	Scene firstScene;

	@Before
	public void setUp() throws Exception {
		novel = new Novel(new ArrayList<Scene>());
		firstScene = new Scene(new Grid(new Dimension(4, 3)),
				new ArrayList<Actor>());
		novel.addScene(firstScene);
	}

	@Test
	public void test() {

		assertNotNull(novel);
	}

	@Test
	public void testSceneCount() {
		assertEquals(novel.getSceneCount(), 1);
	}

	@Test
	public void testSceneById() {
		Scene secondScene = new Scene(new Grid(new Dimension(4, 3)),
				new ArrayList<Actor>());
		novel.addScene(secondScene);
		assertEquals(firstScene, novel.getScenes().get(0));
		assertEquals(secondScene, novel.getScenes().get(1));

	}

	@Test
	public void testGetAndSetName() {
		String testString = "Something, something, evil";
		novel.setName(testString);
		assertEquals(testString, novel.getName());
	}

}
