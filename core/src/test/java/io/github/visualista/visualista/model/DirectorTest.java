package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Director;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DirectorTest {

    @Test
    public void test() {
        Map<Integer, Scene> sceneMap = new HashMap();
        sceneMap.put(4, new Scene(2, new Grid(new Dimension(4, 3))));
        Novel novel = new Novel(3, sceneMap);
        Director director = new Director(novel);
        assertNotNull(director);
    }

}
