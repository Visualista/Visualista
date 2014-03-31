package io.github.visualista.visualista.util;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class MatrixTest {

	@Test(expected=IllegalArgumentException.class)
	public void matrixHeightShouldBeGreaterThan1() {
		new Matrix<Object>(new Dimension(-1,1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void matrixWidthShouldBeGreaterThan1() {
		new Matrix<Object>(new Dimension(1,-1));
	}
	

	@Test
	public void testGetSize() {
		Random rand = new Random();
		int width = rand.nextInt(100)+1;
		int height = rand.nextInt(1000)+1;
		Dimension dim = new Dimension(width,height);
		Matrix<Object> matrix = new Matrix<Object>(dim);
		assertEquals(dim,matrix.getSize());
		
	}

}
