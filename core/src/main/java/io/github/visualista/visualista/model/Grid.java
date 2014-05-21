package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;

public class Grid extends Matrix<Tile> implements IGetGrid {

    @Override
    public String toString() {
        return "Grid [Size=" + getSize() + "]";
    }

    public Grid(Dimension gridSize) {
        super(gridSize);
        fillWith(new TileFactory());
    }

    public Grid(int width, int height) {
        this(new Dimension(width, height));
    }
}
