package io.github.visualista.visualista.util;

public interface IMatrixSet<E> {
	
	public void fillWith(IObjectCreator<E> creator);
	
	public void setAt(Point where, E object);

}
