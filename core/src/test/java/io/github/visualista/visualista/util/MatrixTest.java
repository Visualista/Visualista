package io.github.visualista.visualista.util;

import static org.junit.Assert.*;

import java.util.Random;

import nl.jqno.equalsverifier.EqualsVerifier;

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
		Point point = new Point(rand.nextInt(width), rand.nextInt(height));
		assertNull(matrix.getAt(point));
		matrix.setAt(point, 42);
		assertEquals(new Integer(42), matrix.getAt(point));
	}

	@Test
	public void testFillWith() {
		Random rand = new Random();
		int width = rand.nextInt(140) + 1;
		int height = rand.nextInt(130) + 1;
		Dimension dim = new Dimension(width, height);
		Matrix<Integer> matrix = new Matrix<Integer>(dim);
		matrix.fillWith(new IObjectCreator<Integer>() {
			Random rng = new Random();

			@Override
			public Integer createObject() {
				return new Integer(rng.nextInt());
			}
		});
		for (int i = 0; i < matrix.getSize().getWidth(); ++i) {
			for (int j = 0; j < matrix.getSize().getHeight(); ++j) {
				assertNotNull(matrix.getAt(new Point(i, j)));
			}
		}
	}
	
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Matrix.class).verify();
	}
}
