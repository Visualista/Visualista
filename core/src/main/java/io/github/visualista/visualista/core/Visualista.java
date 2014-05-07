package io.github.visualista.visualista.core;

import java.io.File;
import java.io.FileNotFoundException;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.io.FileLoader;
import io.github.visualista.visualista.io.ObjectFactory;
import io.github.visualista.visualista.io.XStreamManager;

public class Visualista {

	private Novel currentNovel;
	private final XStreamManager xstreamManager;
	private final TileFactory tileFactory;
	private final GridFactory gridFactory;
	private final SceneFactory sceneFactory;
	private final NovelFactory novelFactory;

	public Visualista() {
		xstreamManager = new XStreamManager();
		tileFactory = new TileFactory();
		gridFactory = new GridFactory();
		sceneFactory = new SceneFactory(gridFactory);
		novelFactory = new NovelFactory(sceneFactory);
		currentNovel = novelFactory.createNovel();
	}

	public Novel getCurrentNovel() {
		return currentNovel;
	}

	public void setCurrentNovel(Novel currentNovel) {
		this.currentNovel = currentNovel;
	}

	public void updateCurrentNovel() {

	}

	private void saveNovelIfNeeded() {
		// TODO Code to handle saving
	}

	public void openNovel(File file) {
		saveNovelIfNeeded();
		FileLoader<Novel> fileLoader = new FileLoader<Novel>(
				new ObjectFactory<Novel>(xstreamManager.getMainXStream()));
		try {
			setCurrentNovel(fileLoader.getObjectFromFile(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createNewNovel() {
		saveNovelIfNeeded();
		Novel newNovel = novelFactory.createNovel();
		setCurrentNovel(newNovel);
	}

	public final boolean addNewScene() {
		if(currentNovel!=null){
			currentNovel.addScene(sceneFactory.createScene());
			return true;
		}
		return false;
	}
}
