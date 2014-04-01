package io.github.visualista.visualista.io;

import io.github.visualista.visualista.util.Identifiable;

import com.thoughtworks.xstream.XStream;

public class ObjectFactory<E extends Identifiable> {
	
	private final XStream xstream;
	
	public ObjectFactory(XStream xstream){
		this.xstream = xstream;
	}
	
	@SuppressWarnings("unchecked")
	public E createObject(String readXML){
		return (E)xstream.fromXML(readXML);
	};
}
