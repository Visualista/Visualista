package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Point;

public class Tile {
	private final Actor tileActor;
	
	private final Point gridLocation;
	
	public Tile(Point gridLocation, Actor tileActor){
		this.tileActor = tileActor;
		this.gridLocation = gridLocation;
	}
}
