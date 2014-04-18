package io.github.visualista.visualista.core.model;


public class Tile implements IEditTile {
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
