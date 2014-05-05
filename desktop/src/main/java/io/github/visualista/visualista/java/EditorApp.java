package io.github.visualista.visualista.java;

import io.github.visualista.visualista.core.VisualistaEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

public class EditorApp extends JFrame {
	private static final long serialVersionUID = -3023071056805407189L;

	public EditorApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Container container = getContentPane();
		container.setLayout(new BorderLayout());

		LwjglAWTCanvas canvas = new LwjglAWTCanvas(new VisualistaEditor(
				new DesktopFilePicker(this)), true);
		container.add(canvas.getCanvas(), BorderLayout.CENTER);

		pack();
		setVisible(true);
		setSize(1100, 700);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new EditorApp();
			}
		});
	}
}