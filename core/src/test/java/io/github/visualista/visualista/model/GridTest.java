package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.github.visualista.visualista.util.Dimension;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class GridTest {
    private static final int MAX_HEIGHT = 150;
    private static final int MAX_WIDTH = 150;
    private int width;
    private int height;
    private Dimension dim;
    private Grid dimensionGrid;

    @Before
    public void setUp() throws Exception {
        Random rand = new Random();
        width = rand.nextInt(MAX_WIDTH) + 1;
        height = rand.nextInt(MAX_HEIGHT) + 1;
        dim = new Dimension(width, height);
        dimensionGrid = new Grid(dim);
    }

    @Test
    public void testGridDimensionConstructorSameAsIntIntConstructor() {
        Grid intIntGrid = new Grid(width, height);
        assertThat(dimensionGrid.getSize(), equalTo(intIntGrid.getSize()));
    }

    @Test
    public void testGetSize() {
        assertThat(dim, equalTo(dimensionGrid.getSize()));
    }

    @Test
    public void testToString() {
        assertThat("Grid [Size=" + dim.toString() + "]",
                equalTo(dimensionGrid.toString()));
    }

}
