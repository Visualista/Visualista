package io.github.visualista.visualista.io;

import com.thoughtworks.xstream.XStream;

public class IObjectFactory<E> {
	
	private final XStream xstream;
	
	public IObjectFactory(XStream xstream){
		this.xstream = xstream;
	}
	
	@SuppressWarnings("unchecked")
	public E createObject(String readXML){
		return (E)xstream.fromXML(readXML);
	};
}
