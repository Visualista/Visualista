package io.github.visualista.visualista.model;

import static org.junit.Assert.*;

import java.util.Random;

import io.github.visualista.visualista.core.model.Dimension;
import io.github.visualista.visualista.core.model.GridFactory;

import org.junit.Before;
import org.junit.Test;

public class DimensionTest {
	private Dimension dimension;
	private int width;
	private int height;

	@Before
    public void setUp() throws Exception {
		Random rand = new Random();
		width = rand.nextInt();
		height = rand.nextInt();
		dimension = new Dimension(width,height);
	}

	@Test
	public void testGetWidth() {
		assertEquals(width,dimension.getWidth());
	}

	@Test
	public void testGetHeight() {
		assertEquals(height,dimension.getHeight());
	}
	
	@Test
	public void testEquals(){
		Dimension otherDimension = new Dimension(width,height);
		assertEquals(otherDimension,dimension);
	}

}
