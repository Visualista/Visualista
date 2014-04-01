package io.github.visualista.visualista.io;

import com.thoughtworks.xstream.XStream;

public abstract class IObjectFactory<E> {
	
	private final XStream xstream;
	
	public IObjectFactory(XStream xstream1){
		this.xstream = xstream1;
	}
	
	@SuppressWarnings("unchecked")
	public E createObject(String readXML){
		return (E)xstream.fromXML(readXML);
	};
}
