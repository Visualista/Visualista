package io.github.visualista.visualista.core;

import io.github.visualista.visualista.io.FileLoader;
import io.github.visualista.visualista.io.FileSaveException;
import io.github.visualista.visualista.io.FileSaver;
import io.github.visualista.visualista.io.FolderZipper;
import io.github.visualista.visualista.io.Unzipper;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.ActorFactory;
import io.github.visualista.visualista.model.GridFactory;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.NovelFactory;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.SceneFactory;
import io.github.visualista.visualista.model.TileFactory;
import io.github.visualista.visualista.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

/**
 * Data provider for the Editor Controller, and responsible for creating new
 * Scenes and Actors.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */

public final class VisualistaEditor {

    private Novel currentNovel;
    private final GridFactory gridFactory;
    private final SceneFactory sceneFactory;
    private final NovelFactory novelFactory;
    private final ActorFactory actorFactory;

    public VisualistaEditor() {
        new TileFactory();
        gridFactory = new GridFactory();
        sceneFactory = new SceneFactory(gridFactory);
        novelFactory = new NovelFactory(sceneFactory);
        actorFactory = new ActorFactory();
        currentNovel = novelFactory.createNovel();
    }

    public Novel getCurrentNovel() {
        return currentNovel;
    }

    public void setCurrentNovel(final Novel currentNovel) {
        this.currentNovel = currentNovel;
    }

    public void updateCurrentNovel() {

    }

    public void saveNovel(final File file) {
        final FileSaver<Novel> saver = new FileSaver<Novel>();
        try {
            saver.saveObjectToFile(
                    Gdx.files.local("files" + File.separator + "novel.xml")
                    .file(), currentNovel);
            FolderZipper.zipFolder(Gdx.files.local("files" + File.separator)
                    .file(), file);
        } catch (final FileSaveException e) {
            // TODO error message
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Novel openNovel(final File file) {
        try {
            Unzipper.unzip(file, Gdx.files.local("files" + File.separator)
                    .file());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final FileLoader<Novel> fileLoader = new FileLoader<Novel>();
        try {
            setCurrentNovel(fileLoader.getObjectFromFile(Gdx.files.local(
                    "files" + File.separator + "novel.xml").file()));
        } catch (final FileNotFoundException e) {
            // TODO error message
            e.printStackTrace();
        }

        return currentNovel;

    }

    public Novel createNewNovel() {
        final Novel newNovel = novelFactory.createNovel();
        setCurrentNovel(newNovel);
        return newNovel;
    }

    /**
     * Adds a new scene to the counts of scenes, and selects it if the parameter
     * is true.
     * 
     * @param setCurrent
     *            decides if the new scene is selected.
     */
    public Scene addNewScene(final boolean setCurrent) {
        final Scene newScene = sceneFactory.createScene();
        currentNovel.addScene(newScene);
        if (setCurrent) {
            currentNovel.setCurrentScene(newScene);
        }
        return newScene;
    }

    public IGetActor addNewActor(final Scene scene) {
        Actor actor = actorFactory.createActor();
        scene.addActor(actor);
        return actor;
    }

    public void removeActor(final Actor selectedActor) {
        currentNovel.getCurrentScene().removeActor(selectedActor);
    }

    public String changeCurrentSceneName(final String name) {
        currentNovel.getCurrentScene().setName(name);
        return currentNovel.getCurrentScene().getName();
    }

    public Scene changeSceneName(final Scene scene, final String newName) {
        scene.setName(newName);
        return scene;
    }

    public Actor changeActorName(final Actor actor, final String newName) {
        actor.setName(newName);
        return actor;
    }

    public Actor changeActorImage(final Actor actor, final Image image) {
        actor.setImage(image);
        return actor;
    }

    public Scene changeSceneImage(final Scene scene, final Image image) {
        scene.setImage(image);
        return scene;
    }

    public Scene changeSceneText(final Scene scene, final String string) {
        scene.setStoryText(string);
        return scene;
    }

    public Scene removeScene(final Scene scene) {
        currentNovel.getScenes().remove(scene);
        return scene;
    }

    public Actor getActorFromPosition(final Point position) {
        return currentNovel.getCurrentScene().getGrid().getAt(position)
                .getActor();
    }
}
