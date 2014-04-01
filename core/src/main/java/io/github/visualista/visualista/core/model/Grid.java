package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;

public class Grid {
	Matrix<Tile> tiles;
	
	public Grid(Dimension gridSize){
		tiles = new Matrix<Tile>(gridSize);
	}
	
	public Grid(int width, int height){
		this(new Dimension(width,height));
	}
	
	public Dimension getSize() {
		return tiles.getSize();
	}

	public Matrix<Tile> getTiles() {
		return tiles;
	}

}
