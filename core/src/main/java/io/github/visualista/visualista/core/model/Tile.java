package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Point;

public class Tile {
	private Actor tileActor;
	
	public Tile(Actor tileActor){
		this.tileActor = tileActor;
	}
	
	public Actor getActor(){
		return tileActor;
	}
	
	public void setActor(Actor tileActor){
		this.tileActor = tileActor;
	}
}
