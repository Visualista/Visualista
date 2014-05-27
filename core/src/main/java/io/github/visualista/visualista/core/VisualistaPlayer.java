package io.github.visualista.visualista.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.github.visualista.visualista.io.FileLoader;
import io.github.visualista.visualista.io.Unzipper;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.util.Point;

/** Data provider for the Player Controller, and responsible for creating 
 * new Scenes and Actors.
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */
public class VisualistaPlayer {
    private static final String FILE_FOLDER_PATH = "files" + File.separator;
    
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
        try {
            Unzipper.unzip(file, fileFolderHandle()
                    .file());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final FileLoader<Novel> fileLoader = new FileLoader<Novel>();
        try {
            setCurrentNovel(fileLoader.getObjectFromFile(getNovelFileHandle().file()));
        } catch (final FileNotFoundException e) {
            // TODO error message
            e.printStackTrace();
        }

        return currentNovel;

    }
    
    private FileHandle fileFolderHandle() {
        return Gdx.files.local(FILE_FOLDER_PATH);
    }
    
    private FileHandle getNovelFileHandle() {
        return Gdx.files.local(
                FILE_FOLDER_PATH + "novel.xml");
    }
    
    public Actor getActorFromPosition(final Point position){
        return currentNovel.getCurrentScene().getGrid().getAt(position)
                .getActor();
    }
}
