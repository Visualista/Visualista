package io.github.visualista.visualista.java;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import io.github.visualista.visualista.core.FilePicker;
import io.github.visualista.visualista.core.FilePickerListener;

public class DesktopFilePicker implements FilePicker {

	private Component parent;

	public DesktopFilePicker(Component parent) {
		this.parent = parent;
	}

	void showChooser(final FilePickerListener listener, final boolean fileOpen, FileFilter fileFilter) {
		final JFileChooser chooser = new JFileChooser();
		chooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					listener.filePicked(chooser.getSelectedFile(),fileOpen);
				} else if (e.getActionCommand().equals(
						JFileChooser.CANCEL_SELECTION)) {
					listener.filePicked(null,fileOpen);
				}
				
			}
		});
		chooser.setFileFilter(fileFilter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showDialog(parent, "Select");
	}

	@Override
	public void fileDialog(FilePickerListener listener, boolean open, FileFilter fileFilter) {
		showChooser(listener,open, fileFilter);
		
	}


}
