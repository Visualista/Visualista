package io.github.visualista.visualista.controller;

import io.github.visualista.visualista.core.model.Actor;

public class ActorEvent extends java.util.EventObject{
	
	public ActorEvent(Object source) {
		super(source);
	}

	public Object getSource(){
		return source;
	}
	
	public Actor getActor(){
		if (source.getClass() == Actor.class){
			return (Actor)source;
		}
		return null;
	}
}
