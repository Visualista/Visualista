package io.github.visualista.visualista.core;

import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.SceneFactory;
import io.github.visualista.visualista.core.model.TileFactory;
import io.github.visualista.visualista.io.XStreamManager;

public class Visualista {

	private Novel currentNovel;
	private final XStreamManager xstreamManager;
	
	public Visualista(){
		xstreamManager = new XStreamManager();
		TileFactory tileFactory = new TileFactory();
		GridFactory gridFactory = new GridFactory();
		SceneFactory sceneFactory = new SceneFactory(gridFactory);
		NovelFactory novelFactory = new NovelFactory(sceneFactory);
		
		currentNovel = novelFactory.createNovel();
	}
}
