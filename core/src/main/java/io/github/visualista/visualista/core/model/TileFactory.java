package io.github.visualista.visualista.core.model;

public class TileFactory {

	private final IdGenerator id = new IdGenerator();
	
	private final ActorFactory actorFactory;
	
	public TileFactory(ActorFactory actorFactory){
		this.actorFactory = new ActorFactory();
	}
	
	public Tile createTile(){
		return new Tile(Actor.EMPTY_ACTOR);
	}
}
