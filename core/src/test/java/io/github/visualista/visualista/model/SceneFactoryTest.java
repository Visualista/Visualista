package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class SceneFactoryTest {

    SceneFactory factory;

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
