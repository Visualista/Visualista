package io.github.visualista.visualista.util;

import java.util.HashMap;
import java.util.Map;

public class ReferenceManagerFactory<E extends Identifiable> {

	public ReferenceManagerFactory() {
		
	}
	
	public ReferenceManager<E> createReferenceManager(){
		Map<Integer,E> objectMap = new HashMap<Integer,E>();
		return new ReferenceManager<E>(objectMap);
	}

}
