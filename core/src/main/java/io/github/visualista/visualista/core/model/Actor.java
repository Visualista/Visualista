package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Nameable;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Nameable{

	public static final Actor EMPTY_ACTOR = new Actor();
	private Image image;
	private final List<IAction> actions;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Actor(){
		actions = new ArrayList<IAction>();
		image = new Image();
	}
	
		public List<IAction> getActions(){
		return actions;
	}
	
	public void addAction(IAction action){
		actions.add(action);
	}
	
	public void removeAction(IAction action){
		actions.remove(action);
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image image){
		this.image = image;
	}

	@Override
	public String toString() {
		return "Actor [actions=" + actions + ", name=" + name + "]";
	}
	
	
}
