package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Director;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;
import java.util.*;

import org.junit.Test;

public class DirectorTest {

    @Test
    public void test() {
    	Novel novel = new Novel(3, new ArrayList<Scene>());
    	novel.addScene(new Scene(new Grid(new Dimension(4, 3)),new ArrayList<Actor>()));
        
        Director director = new Director(novel);
        assertNotNull(director);
    }

}
