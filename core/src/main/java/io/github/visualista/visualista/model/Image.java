package io.github.visualista.visualista.model;

import com.badlogic.gdx.files.FileHandle;

public class Image {
    private final FileHandle fileHandle;

    public Image(final FileHandle file) {
        fileHandle = file;
    }
    public FileHandle getFileHandle() {
        return fileHandle;
    }

}
