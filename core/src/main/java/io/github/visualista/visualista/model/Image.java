package io.github.visualista.visualista.model;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;

public class Image {
    private FileHandle fileHandle;
    
    public Image(FileHandle file) {
        this.fileHandle = file;
    }
    public FileHandle getFileHandle() {
        return fileHandle;
    }

}
