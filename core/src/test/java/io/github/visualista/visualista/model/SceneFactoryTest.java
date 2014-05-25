package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SceneFactoryTest {

    private SceneFactory factory;

    @Before
    public void setUp() throws Exception {
        GridFactory gridFactory = new GridFactory();
        factory = new SceneFactory(gridFactory);
    }

    @Test
    public void test() {
        Scene scene = factory.createScene();
        assertThat(scene, notNullValue());
    }

}
