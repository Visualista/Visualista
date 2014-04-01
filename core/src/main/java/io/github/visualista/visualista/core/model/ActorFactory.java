package io.github.visualista.visualista.core.model;

public class ActorFactory {

	private final IdGenerator id;
	
	public ActorFactory() {
		id = new IdGenerator();
	}
	
	public Actor createActor(){
		int actorId = id.generateId();
		return new Actor();
	}
}
