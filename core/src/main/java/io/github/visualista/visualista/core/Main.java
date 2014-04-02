package io.github.visualista.visualista.core;

import java.util.Scanner;
import java.util.Set;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.util.Dimension;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		TileFactory tileFactory = new TileFactory();
		ActorFactory actorFactory = new ActorFactory();
		GridFactory gridFactory = new GridFactory();
		SceneFactory sceneFactory = new SceneFactory(gridFactory);
		NovelFactory novelFactory = new NovelFactory(sceneFactory);

		Novel novel = novelFactory.createNovel();
		System.out
				.println("Number of scenes in novel " + novel.getSceneCount());
		Set<Integer> sceneIdSet = novel.getSceneMap().keySet();
		for (Integer key : sceneIdSet) {
			System.out.println("Dimension of scene in novel "
					+ novel.getSceneMap().get(key).getGrid().getTiles()
							.getSize().toString());
		}
		System.out.println("Add a new scene to the novel");
		System.out.println("Choose the dimension of the grid");
		int x = sc.nextInt();
		int y = sc.nextInt();
		Grid grid = new Grid(new Dimension(x, y));
		Scene scene = new Scene(2, grid);
		novel.getSceneMap().put(2, scene);
		for (Integer key : sceneIdSet) {
			System.out.println("Dimension of scenes in novel "
					+ novel.getSceneMap().get(key).getGrid().getTiles()
							.getSize().toString());
		}
	}

}
