package io.github.visualista.visualista.core;

import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.NovelFactory;
import io.github.visualista.visualista.core.model.SceneFactory;
import io.github.visualista.visualista.core.model.TileFactory;

public class Visualista {

	private Novel currentNovel;
	
	public Visualista(){
		TileFactory tileFactory = new TileFactory();
		GridFactory gridFactory = new GridFactory();
		SceneFactory sceneFactory = new SceneFactory(gridFactory);
		NovelFactory novelFactory = new NovelFactory(sceneFactory);
		
		currentNovel = novelFactory.createNovel();
	}
}
