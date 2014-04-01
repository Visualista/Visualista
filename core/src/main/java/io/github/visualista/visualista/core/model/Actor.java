package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Actor {
	public static final Actor EMPTY_ACTOR = new Actor();
	private Image image;
	private final List<Action> actions;
	private final int id;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Actor(int id){
		this.id = id;
		actions = new ArrayList<Action>();
		image = new Image();
	}
	
	public Actor(){
		this.id = 0;
		actions = null;
		image = null;
	}
	
	public int getId(){
		return id;
	}
	
	public List<Action> getActions(){
		return actions;
	}
	
	public void addAction(Action action){
		actions.add(action);
	}
	
	public void removeAction(Action action){
		actions.remove(action);
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image image){
		this.image = image;
	}
}
