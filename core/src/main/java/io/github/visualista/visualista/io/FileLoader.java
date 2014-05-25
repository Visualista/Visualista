package io.github.visualista.visualista.io;

import io.github.visualista.visualista.io.xstream.XStreamManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader<E> {

    private final ObjectFactory<E> objectFactory;

    public FileLoader(final ObjectFactory<E> objectFactory) {
        this.objectFactory = objectFactory;
    }

    public FileLoader() {
        objectFactory = new ObjectFactory<E>(
                new XStreamManager().getMainXStream());
    }

    public final E getObjectFromFile(final File file)
            throws FileNotFoundException {
        final Scanner sc = new Scanner(file);
        final StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        return objectFactory.createObject(sb.toString());
    }

}
