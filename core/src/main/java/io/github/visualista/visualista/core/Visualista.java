package io.github.visualista.visualista.core;

import java.io.File;

import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.Options;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.core.model.SceneFactory;
import io.github.visualista.visualista.core.model.TileFactory;
import io.github.visualista.visualista.io.XStreamManager;
import io.github.visualista.visualista.util.ReferenceManager;
import io.github.visualista.visualista.util.ReferenceManagerFactory;

public class Visualista {

	private Novel currentNovel;
	private final XStreamManager xstreamManager;
	private final Options options = new Options();
	
	public Visualista(){
		xstreamManager = new XStreamManager();
		TileFactory tileFactory = new TileFactory();
		GridFactory gridFactory = new GridFactory();
		SceneFactory sceneFactory = new SceneFactory(gridFactory);
		NovelFactory novelFactory = new NovelFactory(sceneFactory);
		
		currentNovel = novelFactory.createNovel();
		ReferenceManagerFactory<Scene> rfsFactory = new ReferenceManagerFactory<Scene>();
    	ReferenceManagerFactory<Actor> rfaFactory = new ReferenceManagerFactory<Actor>();
    	
    	//currentNovel.setSceneReferenceManager(rfsFactory.createReferenceManager());
    	//currentNovel.setActorReferenceManager(rfaFactory.createReferenceManager());
	}
}
