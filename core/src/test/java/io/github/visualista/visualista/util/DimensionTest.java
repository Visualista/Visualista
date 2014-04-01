package io.github.visualista.visualista.util;

import static org.junit.Assert.*;

import java.util.Random;

import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.util.Dimension;
import nl.jqno.equalsverifier.EqualsVerifier;

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
	public void equalsContract() {
	    EqualsVerifier.forClass(Dimension.class).verify();
	}
}
