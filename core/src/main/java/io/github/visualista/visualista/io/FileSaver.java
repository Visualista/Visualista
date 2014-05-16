package io.github.visualista.visualista.io;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileSaver<E> {

    private final XStream xstream;

    public FileSaver() {
        this.xstream = new XStreamManager().getMainXStream();
    }

    public void saveObjectToFile(final File outputFile, final E object)
            throws FileSaveException {
        try {
            final FileOutputStream fos = new FileOutputStream(outputFile);
            try {
                xstream.toXML(object, fos);
            } finally {
                fos.close();
            }
        } catch (final IOException e) {
            throw new FileSaveException();
        }
    }

}
