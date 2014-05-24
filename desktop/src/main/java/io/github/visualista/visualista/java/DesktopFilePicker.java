package io.github.visualista.visualista.java;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class DesktopFilePicker implements IFilePicker {
    private static final int SLEEP_TIME = 10;
    private boolean fileIsChoosen = false;
    private synchronized void showChooser(final FilePickerListener listener, final boolean fileOpen,
            FileFilter fileFilter) {
        final JFileChooser chooser = new JFileChooser();

        chooser.addActionListener(new ActionListener() {
            @Override
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
