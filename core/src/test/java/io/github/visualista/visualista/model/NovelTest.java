package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NovelTest {

    @Test
    public void test() {
        Map<Integer, Scene> sceneMap = new HashMap();
        sceneMap.put(4, new Scene(2, new Grid(new Dimension(4, 3))));
        Novel novel = new Novel(3, sceneMap);
        assertNotNull(novel);
    }

    @Test
    public void testSceneCount() {
        Map<Integer, Scene> sceneMap = new HashMap();
        sceneMap.put(4, new Scene(2, new Grid(new Dimension(4, 3))));
        Novel novel = new Novel(3, sceneMap);
        assertEquals(novel.getSceneCount(), 1);
    }

    @Test
    public void testSceneById() {
        Map<Integer, Scene> sceneMap = new HashMap();
        Scene scene = new Scene(2, new Grid(new Dimension(4, 3)));
        sceneMap.put(4, scene);
        Novel novel = new Novel(3, sceneMap);
        assertTrue(scene.equals(novel.getSceneById(4)));
    }
    
    @Test
	public void testGetAndSetName() {
    	Novel novel = new Novel(0, null);
		String testString = "Something, something, evil";
		novel.setName(testString);
		assertEquals(testString, novel.getName());
	}
}
