package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.*;

import org.junit.Before;
import org.junit.Test;

public class SceneTest {
    
    private GridFactory gridFactory;
    private SceneFactory sceneFactory;

    @Before
    public void setUp() throws Exception {
        gridFactory = new GridFactory();
        sceneFactory = new SceneFactory(gridFactory);
    }

    @Test
    public void test() {
        Scene scene = sceneFactory.createScene();
        assertNotNull(scene.getGrid());
    }
    
    @Test
	public void testGetAndSetName() {
		Scene scene = sceneFactory.createScene();
		String testString = "Something, something, dark side";
		scene.setName(testString);
		assertThat(testString, equalTo(scene.getName()));
	}
    
    @Test
    public void testSetAndGetStoryText(){
    	Scene scene = sceneFactory.createScene();
    	String testString = "Something, something, evil";
    	scene.setStoryText(testString);
    	assertThat(scene.getStoryText(),equalTo(testString));
    }
    
    @Test
    public void testAddActorAndGetActorsInScene(){
    	Scene scene = sceneFactory.createScene();
    	Actor actor = new ActorFactory().createActor();
    	scene.addActor(actor);
    	assertThat(actor, equalTo(scene.getActorsInScene().get(0)));
    }

}
