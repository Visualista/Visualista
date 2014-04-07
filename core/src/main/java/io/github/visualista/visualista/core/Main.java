package io.github.visualista.visualista.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.io.CollectionSaver;
import io.github.visualista.visualista.io.XStreamManager;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

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
        for (Scene scene : novel.getScenes()) {
            Matrix<Tile> tiles = scene.getGrid();
            String sceneSize = tiles.getSize().toString();
            System.out.println("Dimension of scene in novel " + sceneSize);
        }
        System.out.println("Add a new scene to the novel");
        System.out.println("Choose the dimension of the grid");
        int x = sc.nextInt();
        int y = sc.nextInt();
        Grid grid = new Grid(new Dimension(x, y));
        Scene otherScene = new Scene(grid, new ArrayList<Actor>());
        novel.addScene(otherScene);
        for (Scene scene : novel.getScenes()) {
            System.out.println("Dimension of scene in novel "
                    + scene.getGrid().getSize()
                            .toString());
        }
        Actor actor = actorFactory.createActor();
        otherScene.addActor(actor);
        System.out.println("Name the actor");
        String name = sc.next();
        actor.setName(name);
        System.out.println("The name of the created actor is " + actor.getName());

        sc.close();
        
		XStreamManager manager = new XStreamManager();
		CollectionSaver<Novel> saver = new CollectionSaver<Novel>(manager.getMainXStream(), new File("C:\\VISUALISTA\\"));
		ArrayList<Novel> arr= new ArrayList<Novel>();
		arr.add(novel);
		saver.saveCollection(arr);
    }

}
