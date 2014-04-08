package io.github.visualista.visualista.java;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import io.github.visualista.visualista.core.FilePicker;
import io.github.visualista.visualista.core.FilePickerListener;

public class DesktopFilePicker implements FilePicker {

	JFileChooser chooser;
	JFrame frame;
	File selectedFile;

	public DesktopFilePicker() {

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
			}
		});

		frame = new JFrame("File chooser in frame");
		frame.add(chooser);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

	@Override
	public void fileDialog(FilePickerListener listener, boolean open) {
		makeUI(listener,open);
		
	}


}
