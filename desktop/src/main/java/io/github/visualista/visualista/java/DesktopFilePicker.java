package io.github.visualista.visualista.java;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import io.github.visualista.visualista.core.FilePicker;
import io.github.visualista.visualista.core.FilePickerListener;

public class DesktopFilePicker implements FilePicker {

	JFileChooser chooser;
	JFrame frame;
	File selectedFile;
	private Component parent;

	public DesktopFilePicker(Component parent) {
		this.parent = parent;
	}

	void makeUI(final FilePickerListener listener, final boolean fileOpen) {
		chooser = new JFileChooser();
		chooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					selectedFile = chooser.getSelectedFile();
				} else if (e.getActionCommand().equals(
						JFileChooser.CANCEL_SELECTION)) {
					selectedFile = null;
				}
				frame.dispose();
				listener.filePicked(selectedFile,fileOpen);
				frame = null;
				chooser = null;
				selectedFile = null;
			}
		});
		chooser.showDialog(parent, "Select");
	}

	@Override
	public void fileDialog(FilePickerListener listener, boolean open) {
		makeUI(listener,open);
		
	}


}
