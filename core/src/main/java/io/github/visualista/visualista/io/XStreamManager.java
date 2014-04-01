package io.github.visualista.visualista.io;


import io.github.visualista.visualista.core.model.Action;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;

import com.thoughtworks.xstream.XStream;

public class XStreamManager {
	
	private final XStream mainXStream;
	
	public XStreamManager(){
		mainXStream = new XStream();
		configureXStream();
	}

	private void configureXStream() {
		mainXStream.alias("Action", Action.class);
		mainXStream.alias("Actor", Actor.class);
		mainXStream.alias("Scene", Scene.class);
		mainXStream.alias("Novel", Novel.class);
		
	}
	
	public XStream getMainXStream(){
		return mainXStream;
	}
}