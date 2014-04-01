package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Actor {
	public static final Actor EMPTY_ACTOR = new Actor();
	private Image image;
	private final List<Action> actions;
	private final int id;
	
	public Actor(int id){
		this.id = id;
		actions = new ArrayList<Action>();
		image = new Image();
	}
	
	public int getId(){
		return id;
	}
}
