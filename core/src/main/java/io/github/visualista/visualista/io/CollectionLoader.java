package io.github.visualista.visualista.io;

import io.github.visualista.visualista.util.Identifiable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;

import com.thoughtworks.xstream.XStream;

public class CollectionLoader<E extends Identifiable> {
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
			if (!file.isDirectory()) {
				try {
					E obj = getObjectFromFile(file);
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

	private E getObjectFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()) {
			sb.append(sc.next());
		}
		sc.close();
		return objectFactory.createObject(sb.toString());
	}

}
