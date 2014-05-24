package io.github.visualista.visualista.util;

public interface IMatrixGet<E> {

    Dimension getSize();

    E getAt(Point from);

}
