package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SceneTest {

    private GridFactory gridFactory;
    private SceneFactory sceneFactory;
    private Scene scene;
    private String testString;
    private Image testImage;

    @Before
    public void setUp() throws Exception {
        gridFactory = new GridFactory();
        sceneFactory = new SceneFactory(gridFactory);
        scene = sceneFactory.createScene();
        testString = "Something creative";
        testImage = new Image(null);
    }

    @Test
    public void testGetGrid() {
        assertNotNull(scene.getGrid());
        assertNotNull(scene.getIGetGrid());
    }

    @Test
    public void testGetAndSetName() {
        scene.setName(testString);
        assertThat(testString, equalTo(scene.getName()));
    }

    @Test
    public void testToString() {
        scene.setName(testString);
        assertThat(testString + " (" + scene.getGrid().getSize() + ")",
                equalTo(scene.toString()));
    }

    @Test
    public void testSetAndGetStoryText() {
        scene.setStoryText(null);
        assertThat("",equalTo(scene.getStoryText()));
        scene.setStoryText(testString);
        assertThat(scene.getStoryText(), equalTo(testString));
    }

    @Test
    public void testAddActorAndGetActorsInSceneAndRemoveActor() {
        Actor actor = new ActorFactory().createActor();
        scene.addActor(actor);
        assertThat(actor, equalTo(scene.getActorsInScene().get(0)));
        scene.removeActor(actor);
        assertThat(false, equalTo(scene.getActorsInScene().contains(actor)));
    }

    @Test
    public void testSetAndGetImage() {
        scene.setImage(testImage);
        assertThat(testImage, equalTo(scene.getImage()));
    }

}
