package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.ReferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class NovelTest {
	Novel novel;
	Scene firstScene;
	
	@Before
    public void setUp() throws Exception {
		Set<Integer> sceneIDs = new HashSet<Integer>();
    	novel = new Novel(3, sceneIDs);
    	novel.setReferenceManager(new ReferenceManager<Scene>());
    	firstScene = new Scene(4, new Grid(new Dimension(4, 3)));
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
    	Scene secondScene = new Scene(7, new Grid(new Dimension(4, 3)));
    	novel.addScene(secondScene);
    	assertEquals(firstScene,novel.getSceneById(4));
    	assertEquals(secondScene,novel.getSceneById(7));
    	
    }
    
    @Test
	public void testGetAndSetName() {
		String testString = "Something, something, evil";
		novel.setName(testString);
		assertEquals(testString, novel.getName());
	}
}
