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
