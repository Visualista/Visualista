package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import io.github.visualista.visualista.util.Dimension;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class NovelTest {
    private static final int GRID_HEIGHT = 3;
    private static final int GRID_WIDTH = 4;
    Novel novel;
    Scene firstScene;
    Scene secondScene;

    @Before
    public void setUp() throws Exception {
        novel = new Novel(new ArrayList<Scene>());
        firstScene = new Scene(
                new Grid(new Dimension(GRID_WIDTH, GRID_HEIGHT)),
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
        secondScene = new Scene(new Grid(new Dimension(GRID_WIDTH,
                GRID_HEIGHT)), new ArrayList<Actor>());
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

    @Test
    public void testSetAndGetCurrentScene() {
        secondScene = new Scene(
                new Grid(new Dimension(GRID_WIDTH, GRID_HEIGHT)),
                new ArrayList<Actor>());
        novel.setCurrentScene(secondScene);
        assertThat(novel.getCurrentScene(), equalTo(secondScene));
    }
}
