package io.github.visualista.visualista.util;

public class Matrix<E> implements IMatrixGet<E>,IMatrixSet<E> {
	private final E[][] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(Dimension size) {
		if(size.getHeight()<1){
			throw new IllegalArgumentException("Matrix height <1 is not allowed. Sent in was: "+size.getHeight());
		}
		if(size.getWidth()<1){
			throw new IllegalArgumentException("Matrix width <1 is not allowed. Sent in was: "+size.getWidth());
		}
		matrix = (E[][])new Object[size.getHeight()][size.getWidth()];
	}
	
	public Dimension getSize(){
		return new Dimension(matrix[0].length,matrix.length);
	}

	@Override
	public void fillWith(IObjectCreator<E> creator) {
		for(int i=0;i<matrix.length;++i){
			for(int j=0;j<matrix[i].length;++j){
				matrix[i][j] = creator.createObject();
			}
		}
		
	}

	@Override
	public E get(Point from) {
		return matrix[from.getY()][from.getX()];
	}

	@Override
	public void setAt(Point where, E object) {
		matrix[where.getY()][where.getX()] = object;
	}

}
