package io.github.visualista.visualista.io;

import com.thoughtworks.xstream.XStream;

import io.github.visualista.visualista.util.Nameable;

import java.io.File;
import java.util.Collection;



public final class CollectionSaver<E extends Nameable> {

    private File folder;

    public CollectionSaver(final XStream xstream,final File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        } else if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Not a folder");
        }
        this.folder = folder;
    }

    public void saveCollection(final Collection<E> objects) {
        final FileSaver<E> fileSaver = new FileSaver<E>();
        for (E object : objects) {
            final File outputFile = new File(folder.getAbsolutePath(),
                    object.getName() + ".vis");
            try {
                fileSaver.saveObjectToFile(outputFile, object);
            } catch (FileSaveException e) {
                e.printStackTrace();
            }

        }
    }

}
