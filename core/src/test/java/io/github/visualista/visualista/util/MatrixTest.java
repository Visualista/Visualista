package io.github.visualista.visualista.util;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class MatrixTest {

	@Test(expected = IllegalArgumentException.class)
	public void matrixHeightShouldBeGreaterThan1() {
		new Matrix<Object>(new Dimension(-1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void matrixWidthShouldBeGreaterThan1() {
		new Matrix<Object>(new Dimension(1, -1));
	}

	@Test
	public void testGetSize() {
		Random rand = new Random();
		int width = rand.nextInt(140) + 1;
		int height = rand.nextInt(130) + 1;
		Dimension dim = new Dimension(width, height);
		Matrix<Object> matrix = new Matrix<Object>(dim);
		assertEquals(dim, matrix.getSize());
	}
	
	@Test
	public void testGetAndSetAtPosition() {
		Random rand = new Random();
		int width = rand.nextInt(140) + 1;
		int height = rand.nextInt(130) + 1;
		Dimension dim = new Dimension(width, height);
		Matrix<Integer> matrix = new Matrix<Integer>(dim);
		Point point = new Point(rand.nextInt(width),rand.nextInt(height));
		assertNull(matrix.get(point));
		matrix.setAt(point, 42);
		assertEquals(new Integer(42),matrix.get(point));
	}

}
