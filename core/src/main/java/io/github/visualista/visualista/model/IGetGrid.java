package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Point;

public interface IGetGrid {

    Dimension getSize();

    IGetTile getAt(Point from);

}
