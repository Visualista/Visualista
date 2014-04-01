package io.github.visualista.visualista.core.model;

public class TileFactory {

	private final IdGenerator id;
	
	private final ActorFactory actorFactory;
	
	public TileFactory(ActorFactory actorFactory){
		id = new IdGenerator();
		this.actorFactory = new ActorFactory();
	}
	
	public Tile createTile(){
		int tileId = id.generateId();
		return new Tile(Actor.EMPTY_ACTOR);
	}
}
