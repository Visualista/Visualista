package io.github.visualista.visualista.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader<E> {

    private final ObjectFactory<E> objectFactory;

    public FileLoader(ObjectFactory<E> objectFactory) {
        this.objectFactory = objectFactory;
    }

    public FileLoader() {
        objectFactory = new ObjectFactory<E>(new XStreamManager().getMainXStream());
    }

    public E getObjectFromFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        return objectFactory.createObject(sb.toString());
    }

}
