package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SetSceneActionTest {

    private GridFactory gridFactory;
    private SceneFactory sceneFactory;
    private Scene scene;
    private SetSceneAction setSceneAction;

    @Before
    public void setUp() {
        gridFactory = new GridFactory();
        sceneFactory = new SceneFactory(gridFactory);
        scene = sceneFactory.createScene();
        setSceneAction = new SetSceneAction(scene);
    }

    @Test
    public void testSetAndGetTargetScene() {
        Scene scene2 = sceneFactory.createScene();
        setSceneAction.setTargetScene(scene2);
        assertThat(setSceneAction.getTargetScene(), equalTo(scene2));
    }

    @Test
    public void testToStringAndGetExplainatoryName() {
        assertThat(setSceneAction.toString(),
                equalTo("Switch to the " + scene.getName() + " Scene"));
    }

    @Test
    public void testGetActionData() {
        assertThat((Scene) setSceneAction.getActionData(), equalTo(scene));
    }
}
