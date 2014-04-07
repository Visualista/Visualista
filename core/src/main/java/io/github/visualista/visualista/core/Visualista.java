package io.github.visualista.visualista.core;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.io.XStreamManager;

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
	}
}
