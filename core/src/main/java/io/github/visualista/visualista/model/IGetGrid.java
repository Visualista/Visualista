package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Point;

public interface IGetGrid {

    public Dimension getSize();

    public IGetTile getAt(Point from);

}
