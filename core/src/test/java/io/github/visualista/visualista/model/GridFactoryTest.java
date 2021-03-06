package io.github.visualista.visualista.model;

import static org.junit.Assert.assertEquals;
import io.github.visualista.visualista.util.Dimension;

import org.junit.Before;
import org.junit.Test;

public class GridFactoryTest {
    private static final int GRID_DEFAULT_HEIGHT = 5;
    private static final int GRID_DEFAULT_WIDTH = 5;
    private GridFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new GridFactory();
    }

    @Test
    public void testCreateGrid() {
        Grid grid = factory.createGrid();
        assertEquals(new Dimension(GRID_DEFAULT_WIDTH, GRID_DEFAULT_HEIGHT),
                grid.getSize());
    }

}
