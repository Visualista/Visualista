package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.util.Dimension;

import org.junit.Before;
import org.junit.Test;

public class GridFactoryTest {
	GridFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new GridFactory();
	}

	@Test
	public void testCreateGrid() {
		Grid grid = factory.createGrid();
		assertEquals(new Dimension(5, 5), grid.getSize());
	}

}
