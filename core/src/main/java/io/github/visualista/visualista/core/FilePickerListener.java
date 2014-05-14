package io.github.visualista.visualista.core;

import java.io.File;

public interface FilePickerListener {
    void fileOpened(File selectedFile);
    void fileSaved(File selectedFile);
}
