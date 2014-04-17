package io.github.visualista.visualista.core;

import javax.swing.filechooser.FileFilter;


public interface FilePicker {

	void fileDialog(FilePickerListener listener, boolean open,
			FileFilter fileFilter);
}
