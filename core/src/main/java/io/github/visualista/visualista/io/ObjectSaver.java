package io.github.visualista.visualista.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;

import io.github.visualista.visualista.util.Identifiable;

public class ObjectSaver<E extends Identifiable> {

	private final XStream xstream;
	private File folder;

	public ObjectSaver(XStream xstream, File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		} else if (!folder.isDirectory()) {
			throw new IllegalArgumentException("Not a folder");
		}
		this.xstream = xstream;
		this.folder = folder;
	}
	
	public void saveCollection(Collection<E> objects){
		for(E object : objects){
			File outputFile = new File(folder.getAbsolutePath(),object.getId()+".vis");
			
			try {
				FileOutputStream fos= new FileOutputStream(outputFile);
				try{
				xstream.toXML(object, fos);
				}
				finally{
					fos.close();
				}
			}catch(IOException e){
			}
			

		}
	}
	
}
