package io.github.visualista.visualista.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

public class FileSaver<E> {

    private final XStream xstream;

    public FileSaver(XStream xstream) {
        this.xstream = xstream;
    }

    public void saveObjectToFile(File outputFile, E object) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            try {
                xstream.toXML(object, fos);
            } finally {
                fos.close();
            }
        } catch (IOException e) {
        }
    }

}
