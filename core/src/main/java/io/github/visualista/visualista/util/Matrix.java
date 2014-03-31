package io.github.visualista.visualista.util;

public class Matrix<E> {
	E[][] matrix;

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

}
