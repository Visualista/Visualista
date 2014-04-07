package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Point;

public class ReplaceTileAction implements Action{
	
	private Point targetTile;
	private Actor replacementActor;
	
	public ReplaceTileAction(Point targetTile, Actor replacementActor){
		this.targetTile = targetTile;
		this.replacementActor = replacementActor;
	}
	
	public void callAction(){
		
	}
	
	public Point getTargetTile(){
		return targetTile;
	}
	
	public Actor getReplacementActor(){
		return replacementActor;
	}
	
	public void setTargetTile(Point targetTile){
		this.targetTile = targetTile;
	}
	
	public void setReplacementActor(Actor replacementActor){
		this.replacementActor = replacementActor;
	}
	
	public String toString(){
		return "SetTile(" + replacementActor.toString() + " | " + targetTile.getX() + 
				"," + targetTile.getY() + ")";
	}
}
