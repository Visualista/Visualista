package io.github.visualista.visualista.core;

import java.util.Set;

import io.github.visualista.visualista.core.model.*;

public class Main {

	public static void main(String[] args) {
		TileFactory tileFactory = new TileFactory();
		ActorFactory actorFactory = new ActorFactory();
		GridFactory gridFactory = new GridFactory();
		SceneFactory sceneFactory = new SceneFactory(gridFactory);
		NovelFactory novelFactory = new NovelFactory(sceneFactory);
		
		Novel novel = novelFactory.createNovel();
		System.out.println("Number of scenes in novel" + novel.getSceneCount());
		Set<Integer> sceneIdSet = novel.getSceneMap().keySet();
		Scene scene = novel.getSceneMap().get(1);
		System.out.println(scene.getGrid().getTiles().getSize().toString());
	}
	
	

}
