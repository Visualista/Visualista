package io.github.visualista.visualista.core.model;


public class Tile {
	private Actor tileActor;
	//int actorId = 0;
	
	
	public Tile(Actor tileActor){
		this.tileActor = tileActor;
		//if(tileActor!=null){
		//	actorId = tileActor.getId();
		//}
	}
	
	public Actor getActor(){
		return tileActor;
	}
	//public int getActorId(){
	//	return actorId;
	//}
	
	public void setActor(Actor tileActor){
		this.tileActor = tileActor;
		//if(tileActor!=null){
		//	actorId = tileActor.getId();
		//}
	}
}
