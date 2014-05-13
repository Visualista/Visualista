package io.github.visualista.visualista.core;

import javax.swing.filechooser.FileFilter;

public interface IFilePicker {

    void fileDialog(FilePickerListener listener, boolean open,
            FileFilter fileFilter);
}
