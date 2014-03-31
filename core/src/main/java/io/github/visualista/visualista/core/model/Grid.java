package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Dimension;

public class Grid {
	private final Dimension gridSize;
	
	public Grid(Dimension gridSize){
		this.gridSize = gridSize;
	}
	
	public Grid(int width, int height){
		this.gridSize = new Dimension(width,height);
	}
	
	public Dimension getSize() {
		return gridSize;
	}

}
