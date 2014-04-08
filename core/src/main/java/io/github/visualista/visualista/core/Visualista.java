package io.github.visualista.visualista.core;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.io.XStreamManager;

public class Visualista {

	private Novel currentNovel;
	private final XStreamManager xstreamManager;
	private final TileFactory tileFactory;
	private final GridFactory gridFactory;
	private final SceneFactory sceneFactory;
	private final NovelFactory novelFactory;
	
	public Visualista(){
		xstreamManager = new XStreamManager();
		tileFactory = new TileFactory();
		gridFactory = new GridFactory();
		sceneFactory = new SceneFactory(gridFactory);
		novelFactory = new NovelFactory(sceneFactory);
		currentNovel = novelFactory.createNovel();
	}
	
	public Novel getCurrentNovel(){
		return currentNovel;
	}
	
	public void setCurrentNovel(Novel currentNovel){
		this.currentNovel = currentNovel;
	}
	
	public void updateCurrentNovel(){
		
	}
}
