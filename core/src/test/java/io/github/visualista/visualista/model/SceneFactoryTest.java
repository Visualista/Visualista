package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.core.model.SceneFactory;

import org.junit.Test;

public class SceneFactoryTest {

    @Test
    public void test() {
        SceneFactory factory = new SceneFactory();
        Scene scene = factory.createScene();
        assertNotNull(scene);
    }

}
