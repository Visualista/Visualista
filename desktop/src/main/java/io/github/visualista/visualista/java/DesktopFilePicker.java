package io.github.visualista.visualista.java;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.badlogic.gdx.Gdx;

import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.core.FilePickerListener;

public class DesktopFilePicker implements IFilePicker {
    private static final int SLEEP_TIME = 10;
    private boolean fileIsChoosen = false;
    private synchronized void showChooser(final FilePickerListener listener, final boolean fileOpen,
            FileFilter fileFilter) {
        final JFileChooser chooser = new JFileChooser();
        
        chooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileIsChoosen = true;
            }
        });
        chooser.setFileFilter(fileFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (fileOpen) {
            chooser.showDialog(null, "Open");
        } else {
            chooser.showDialog(null, "Save");
        }
        while(!fileIsChoosen){
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e1) {
            }
        }
        if (fileOpen) {
            listener.fileOpened(chooser.getSelectedFile());
        } else {
            listener.fileSaved(chooser.getSelectedFile());
        }
    }

    @Override
    public void openFileDialog(FilePickerListener listener,
            FileFilter fileFilter) {
        showChooser(listener, true, fileFilter);

    }

    @Override
    public void saveFileDialog(FilePickerListener listener,
            FileFilter fileFilter) {
        showChooser(listener, false, fileFilter);

    }

}
