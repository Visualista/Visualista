package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Director;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.ReferenceManager;
import io.github.visualista.visualista.util.ReferenceManagerFactory;

import java.util.*;

import org.junit.Test;

public class DirectorTest {

    @Test
    public void test() {
    	Set<Integer> sceneIDs = new HashSet<Integer>();
    	Set<Integer> actorIDs = new HashSet<Integer>();
    	Novel novel = new Novel(3, new ArrayList<Scene>());
    	ReferenceManagerFactory<Scene> rfsFactory = new ReferenceManagerFactory<Scene>();
    	ReferenceManagerFactory<Actor> rfaFactory = new ReferenceManagerFactory<Actor>();
    	
    	//novel.setSceneReferenceManager(rfsFactory.createReferenceManager());
    	//novel.setActorReferenceManager(rfaFactory.createReferenceManager());
    	novel.addScene(new Scene(4, new Grid(new Dimension(4, 3)),new ArrayList<Actor>()));
        
        Director director = new Director(novel);
        assertNotNull(director);
    }

}
