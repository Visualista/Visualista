package io.github.visualista.visualista.util;

public interface IMatrixSet<E> extends IMatrixGet<E> {

    void fillWith(IObjectCreator<E> creator);

    void setAt(Point where, E object);

}
