package io.github.visualista.visualista.util;

import io.github.visualista.visualista.io.ObjectFactory;

import java.io.File;

import com.thoughtworks.xstream.XStream;

public class ReferenceManager<E> {

	private final ObjectFactory<E> objectFactory;
	
	private final XStream xstream;
	
	public ReferenceManager(File folder){
		xstream = new XStream();
		objectFactory = new ObjectFactory<E>(xstream);
		if (!folder.isDirectory()){
			throw new IllegalArgumentException("Not a file");
		} else if (!folder.exists()){
			folder.mkdirs();
			System.out.println("Creating " + folder.toString());
		} else {
			for (final File file : folder.listFiles()){
				
			}
		}
		
	}
}
