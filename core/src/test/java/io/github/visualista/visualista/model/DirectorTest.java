package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Director;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.ReferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class DirectorTest {

    @Test
    public void test() {
    	Set<Integer> sceneIDs = new HashSet<Integer>();
    	Novel novel = new Novel(3, sceneIDs);
    	novel.setReferenceManager(new ReferenceManager<Scene>());
    	novel.addScene(4, new Scene(2, new Grid(new Dimension(4, 3))));
        
        Director director = new Director(novel);
        assertNotNull(director);
    }

}
