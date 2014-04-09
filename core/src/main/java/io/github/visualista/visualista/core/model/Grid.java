package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;

public class Grid extends Matrix<Tile> {

	
	public Grid(Dimension gridSize){
		super(gridSize);
		fillWith(new TileFactory());
	}
	
	public Grid(int width, int height){
		this(new Dimension(width,height));
	}
}
