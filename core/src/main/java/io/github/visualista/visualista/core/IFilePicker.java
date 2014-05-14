package io.github.visualista.visualista.core;

import javax.swing.filechooser.FileFilter;

public interface IFilePicker {

    void openFileDialog(FilePickerListener listener, FileFilter fileFilter);

    void saveFileDialog(FilePickerListener listener, FileFilter fileFilter);
}
