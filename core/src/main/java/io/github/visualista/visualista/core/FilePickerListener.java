package io.github.visualista.visualista.core;

import java.io.File;

public interface FilePickerListener {
	void filePicked(File selectedFile, boolean fileOpen);
}
