package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Dimension;
import io.github.visualista.visualista.core.model.Grid;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class GridTest {
	private int width;
	private int height;

	@Before
	public void setUp() throws Exception {
		Random rand = new Random();
		width = rand.nextInt();
		height = rand.nextInt();
	}

	@Test
	public void testGridDimensionConstructorSameAsIntIntConstructor() {
		Dimension dim = new Dimension(width, height);
		Grid dimensionGrid = new Grid(dim);
		Grid intIntGrid = new Grid(width, height);
		assertEquals(dimensionGrid.getSize(), intIntGrid.getSize());
	}

	@Test
	public void testGetSize() {
		Dimension dim = new Dimension(width, height);
		Grid dimensionGrid = new Grid(dim);
		assertEquals(dim,dimensionGrid.getSize());
	}

}
