package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Scene;

import java.util.Random;

import org.junit.Test;

public class SceneTest {

    @Test
    public void test() {
        Random rand = new Random();
        int id = rand.nextInt();
        Scene scene = new Scene(id);
        assertEquals(id,scene.getId());
    }

}
