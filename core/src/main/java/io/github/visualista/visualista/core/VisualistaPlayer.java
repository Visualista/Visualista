package io.github.visualista.visualista.core;

import java.io.File;
import java.io.FileNotFoundException;

import io.github.visualista.visualista.io.FileLoader;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.util.Point;

/** Data provider for the Player Controller, and responsible for creating 
 * new Scenes and Actors.
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */
public class VisualistaPlayer {
    private Novel currentNovel;

    public VisualistaPlayer() {
    }

    public Novel getCurrentNovel() {
        return currentNovel;
    }

    public void setCurrentNovel(final Novel currentNovel) {
        this.currentNovel = currentNovel;
    }

    public void updateCurrentNovel() {

    }

    public Novel openNovel(final File file) {
        final FileLoader<Novel> fileLoader = new FileLoader<Novel>();
        try {
            setCurrentNovel(fileLoader.getObjectFromFile(file));
            currentNovel.setCurrentScene(currentNovel.getScenes().get(0));
        } catch (final FileNotFoundException e) {
            // TODO error message
            e.printStackTrace();
        }
        return currentNovel;

    }
    public Actor getActorFromPosition(final Point position){
        return currentNovel.getCurrentScene().getGrid().getAt(position)
                .getActor();
    }
}
