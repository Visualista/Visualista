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
    void showChooser(final FilePickerListener listener, final boolean fileOpen,
            FileFilter fileFilter) {
        final JFileChooser chooser = new JFileChooser();
        chooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    if (fileOpen) {
                        listener.fileOpened(chooser.getSelectedFile());
                    } else {
                        listener.fileSaved(chooser.getSelectedFile());
                    }

                } else if (e.getActionCommand().equals(
                        JFileChooser.CANCEL_SELECTION)) {
                }

            }
        });
        chooser.setFileFilter(fileFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (fileOpen) {
            chooser.showDialog(null, "Open");
        } else {
            chooser.showDialog(null, "Save");
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
