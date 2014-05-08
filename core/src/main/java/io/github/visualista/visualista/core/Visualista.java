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

    /**
     * Adds a new scene to the counts of scenes, and selects it if the parameter is true.
     * @param setCurrent decides if the new scene is selected.
     */
    public void addNewScene(boolean setCurrent) {
        Scene newScene = sceneFactory.createScene();
        currentNovel.addScene(newScene);
        if (setCurrent){
            currentNovel.setCurrentScene(newScene);
        }
    }

    public void addNewActor() {   
        currentNovel.getCurrentScene().addActor(actorFactory.createActor());
       
    }
    
    public void removeActor(Actor selectedActor){
        currentNovel.getCurrentScene().removeActor(selectedActor);
    }
}
