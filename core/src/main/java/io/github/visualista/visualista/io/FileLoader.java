package io.github.visualista.visualista.io;

import io.github.visualista.visualista.util.Nameable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader<E extends Nameable> {

	private final ObjectFactory<E> objectFactory;

	public FileLoader(ObjectFactory<E> objectFactory) {
		this.objectFactory = objectFactory;
	}

	public E getObjectFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()) {
			sb.append(sc.next());
		}
		sc.close();
		return objectFactory.createObject(sb.toString());
	}

}
