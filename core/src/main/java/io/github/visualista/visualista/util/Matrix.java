package io.github.visualista.visualista.util;

public class Matrix<E> implements IMatrixGet<E>,IMatrixSet<E> {
	private final Row<E>[] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(Dimension size) {
		if(size.getHeight()<1){
			throw new IllegalArgumentException("Matrix height <1 is not allowed. Sent in was: "+size.getHeight());
		}
		if(size.getWidth()<1){
			throw new IllegalArgumentException("Matrix width <1 is not allowed. Sent in was: "+size.getWidth());
		}
		matrix = (Row<E>[])new Row[size.getHeight()];
		for(int i = 0;i<matrix.length;++i){
			matrix[i] = new Row(size.getWidth());
		}
	}
	
	public Dimension getSize(){
		return new Dimension(matrix[0].getLength(),matrix.length);
	}

	@Override
	public void fillWith(IObjectCreator<E> creator) {
		for(int i=0;i<matrix.length;++i){
			for(int j=0;j<matrix[i].getLength();++j){
				matrix[i].setAt(j, creator.createObject());
			}
		}
		
	}

	@Override
	public E getAt(Point from) {
		return matrix[from.getY()].getAt(from.getX());
	}

	@Override
	public void setAt(Point where, E object) {
		matrix[where.getY()].setAt(where.getX(), object);
	}

}
