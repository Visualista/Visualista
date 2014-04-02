package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.core.model.SceneFactory;

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
        assertNotNull(scene);
    }

}
