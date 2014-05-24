package io.github.visualista.visualista.io;


import com.thoughtworks.xstream.XStream;

public class ObjectFactory<E> {

    private final XStream xstream;

    public ObjectFactory(final XStream xstream) {
        this.xstream = xstream;
    }

    @SuppressWarnings("unchecked")
    public E createObject(final String readXML) {
        return (E) xstream.fromXML(readXML);
    };
}
