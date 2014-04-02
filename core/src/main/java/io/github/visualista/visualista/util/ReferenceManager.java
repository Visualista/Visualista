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

	private final ObjectFactory<E> objectFactory;	
	private XStream xstream;
	private Map<Integer, E> objectMap;
	
	public ReferenceManager(File folder){
		objectFactory = new ObjectFactory<E>(xstream);
		objectMap = new HashMap<Integer, E>();
		
		if (!folder.exists()){
			folder.mkdirs();
			System.out.println("Creating " + folder.toString());
		}else if (!folder.isDirectory()){
			throw new IllegalArgumentException("Not a directory!");
		} else {
			createObjects(folder);
		}
		
	}

	private void createObjects(File folder){
		for (final File file : folder.listFiles()){
			if(!file.isDirectory()){
				try {
					E obj = getObjectFromFile(file);
					objectMap.put(obj.getId(), obj);
				} catch (FileNotFoundException fnfe){
					System.out.println("File not readable!");
				}
			}
			
		}
		
	}
	
	public void setXStream(XStream xstream){
		this.xstream = xstream;
	}
	
	private E getObjectFromFile(File file) throws FileNotFoundException{
		Scanner sc = new Scanner(file);
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()){
			sb.append(sc.next());
		}
		return objectFactory.createObject(sb.toString());
	}
}
