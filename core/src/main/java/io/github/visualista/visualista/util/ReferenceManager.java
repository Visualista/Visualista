package io.github.visualista.visualista.util;


import io.github.visualista.visualista.io.ObjectFactory;
import io.github.visualista.visualista.util.Identifiable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;

//TODO: Make a real class

public class ReferenceManager<E extends Identifiable> {
	
	private Map<Integer, E> objectMap;
	
	public ReferenceManager(Map<Integer, E> objectMap){
		this.objectMap = objectMap;
	}

	public void addObject(E object) {
		objectMap.put(object.getId(), object);
	}

	public E getObject(Integer id) {
		return objectMap.get(id);
	}
}
