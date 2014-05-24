package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Matrix;

public class Grid extends Matrix<Tile> implements IGetGrid {

    public Grid(final Dimension gridSize) {
        super(gridSize);
        fillWith(new TileFactory());
    }

    public Grid(final int width, final int height) {
        this(new Dimension(width, height));
    }

    @Override
    public String toString() {
        return "Grid [Size=" + getSize() + "]";
    }

}
