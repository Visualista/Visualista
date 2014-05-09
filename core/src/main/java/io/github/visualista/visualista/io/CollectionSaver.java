package io.github.visualista.visualista.io;

import java.io.File;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;

import io.github.visualista.visualista.util.Nameable;

public class CollectionSaver<E extends Nameable> {

    private final XStream xstream;
    private File folder;

    public CollectionSaver(XStream xstream, File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        } else if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Not a folder");
        }
        this.xstream = xstream;
        this.folder = folder;
    }

    public void saveCollection(Collection<E> objects) {
        FileSaver<E> fileSaver = new FileSaver<E>(xstream);
        for (E object : objects) {
            File outputFile = new File(folder.getAbsolutePath(),
                    object.getName() + ".vis");
            fileSaver.saveObjectToFile(outputFile, object);

        }
    }

}
