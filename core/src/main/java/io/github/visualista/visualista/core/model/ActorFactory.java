package io.github.visualista.visualista.core.model;

public class ActorFactory {

	private final IdGenerator idGen;
	
	public ActorFactory() {
		idGen = new IdGenerator();
	}
	
	public Actor createActor(){
		int id = idGen.generateId();
		return new Actor(id);
	}
}
