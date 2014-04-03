package io.github.visualista.visualista.core;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import io.github.visualista.visualista.core.model.*;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;

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
            Grid grid = scene.getGrid();
            Matrix<Tile> tiles = grid.getTiles();
            String sceneSize = tiles.getSize().toString();
            System.out.println("Dimension of scene in novel " + sceneSize);
        }
        System.out.println("Add a new scene to the novel");
        System.out.println("Choose the dimension of the grid");
        int x = sc.nextInt();
        int y = sc.nextInt();
        Grid grid = new Grid(new Dimension(x, y));
        Scene otherScene = new Scene(2, grid, new ArrayList<Actor>());
        novel.addScene(otherScene);
        for (Scene scene : novel.getScenes()) {
            System.out.println("Dimension of scene in novel "
                    + scene.getGrid().getTiles().getSize()
                            .toString());
        }
        Actor actor = actorFactory.createActor();
        otherScene.addActor(actor);
        System.out.println("The id of the created actor " + actor.getId());

        sc.close();
    }

}
