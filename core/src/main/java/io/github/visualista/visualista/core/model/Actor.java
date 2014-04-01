package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Actor {
	public static final Actor EMPTY_ACTOR = new Actor();
	private Image image;
	private final List<Action> actions;
	
	public Actor(){
		actions = new ArrayList<Action>();
		image = new Image();
	}
}
