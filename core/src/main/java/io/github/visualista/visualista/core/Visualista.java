package io.github.visualista.visualista.core;

import java.io.File;
import java.io.FileNotFoundException;

import io.github.visualista.visualista.model.*;
import io.github.visualista.visualista.io.FileLoader;
import io.github.visualista.visualista.io.FileSaver;
import io.github.visualista.visualista.io.ObjectFactory;
import io.github.visualista.visualista.io.XStreamManager;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.ActorFactory;
import io.github.visualista.visualista.model.GridFactory;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.NovelFactory;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.SceneFactory;
import io.github.visualista.visualista.model.TileFactory;
import io.github.visualista.visualista.util.Point;

public class Visualista {

    private Novel currentNovel;
    private final XStreamManager xstreamManager;
    private final TileFactory tileFactory;
    private final GridFactory gridFactory;
    private final SceneFactory sceneFactory;
    private final NovelFactory novelFactory;
    private final ActorFactory actorFactory;

    public Visualista() {
        xstreamManager = new XStreamManager();
        tileFactory = new TileFactory();
        gridFactory = new GridFactory();
        sceneFactory = new SceneFactory(gridFactory);
        novelFactory = new NovelFactory(sceneFactory);
        actorFactory = new ActorFactory();
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

    public void saveNovel(File file) {
        FileSaver<Novel> saver = new FileSaver<Novel>(xstreamManager.getMainXStream());
        saver.saveObjectToFile(file, currentNovel);
    }

    public Novel openNovel(File file) {
        FileLoader<Novel> fileLoader = new FileLoader<Novel>();
        try {
            setCurrentNovel(fileLoader.getObjectFromFile(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentNovel;

    }

    public Novel createNewNovel() {
        Novel newNovel = novelFactory.createNovel();
        setCurrentNovel(newNovel);
        return newNovel;
    }

    /**
     * Adds a new scene to the counts of scenes, and selects it if the parameter is true.
     * @param setCurrent decides if the new scene is selected.
     */
    public Scene addNewScene(boolean setCurrent) {
        Scene newScene = sceneFactory.createScene();
        currentNovel.addScene(newScene);
        if (setCurrent){
            currentNovel.setCurrentScene(newScene);
        }
        return newScene;
    }

    public void addNewActor(Scene scene) {   
        scene.addActor(actorFactory.createActor());
       
    }
    
    public void removeActor(Actor selectedActor){
        currentNovel.getCurrentScene().removeActor(selectedActor);
    }

    public String changeCurrentSceneName(String name) {
        currentNovel.getCurrentScene().setName(name);
        return currentNovel.getCurrentScene().getName();
    }

    public Scene changeSceneName(Scene scene, String newName) {
        scene.setName(newName);
        return scene;
    }

    public Actor changeActorName(Actor actor, String newName) {
        actor.setName(newName);
        return actor;
    }

    public Actor changeActorImage(Actor actor, Image image) {
        actor.setImage(image);
        return actor;
    }

    public Scene changeSceneImage(Scene scene, Image image) {
        scene.setImage(image);
        return scene;
    }

    public Scene changeSceneText(Scene scene, String string) {
        scene.setStoryText(string);
        return scene;
    }

    public Scene removeScene(Scene scene) {
        currentNovel.getScenes().remove(scene);
        return scene;
    }
    
    public Actor getActorFromPosition(Point position){
        return currentNovel.getCurrentScene().getGrid().getAt(position).getActor();
    }
}
