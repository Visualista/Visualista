package io.github.visualista.visualista.io;

import io.github.visualista.visualista.util.Nameable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;

import com.thoughtworks.xstream.XStream;

public class CollectionLoader<E extends Nameable> {
	private Collection<E> objects = new ArrayList<E>();

	private final ObjectFactory<E> objectFactory;

	public CollectionLoader(XStream xstream, File folder) {
		objectFactory = new ObjectFactory<E>(xstream);

		if (!folder.exists()) {
			folder.mkdirs();
			System.out.println("Creating " + folder.toString());
		} else if (!folder.isDirectory()) {
			throw new IllegalArgumentException("Not a directory!");
		}
		loadObjects(folder);

	}

	private void loadObjects(File folder) {

		for (final File file : folder.listFiles()) {
			FileLoader<E> fileLoader = new FileLoader<E>(objectFactory);
			if (!file.isDirectory()) {
				try {
					E obj = fileLoader.getObjectFromFile(file);
					objects.add(obj);
				} catch (FileNotFoundException fnfe) {
					System.out.println("File not readable!");
				}
			}

		}
	}

	public Collection<E> getObjects() {
		return objects;
	}
}
