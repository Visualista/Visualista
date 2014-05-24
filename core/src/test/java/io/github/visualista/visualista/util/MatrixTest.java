package io.github.visualista.visualista.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Random;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

    private static final int SOME_INTEGER = 42;
    private static final int MATRIX_MAX_WIDTH = 140;
    private static final int MATRIX_MAX_HEIGHT = 130;
    private Dimension dim;
    private Matrix<Integer> matrix;
    private Random rand;

    @Before
    public void setUp() {
        rand = new Random();
        int width = rand.nextInt(MATRIX_MAX_WIDTH) + 1;
        int height = rand.nextInt(MATRIX_MAX_HEIGHT) + 1;
        dim = new Dimension(width, height);
        matrix = new Matrix<Integer>(dim);
    }

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

        assertEquals(dim, matrix.getSize());
    }

    @Test
    public void testGetAndSetAtPosition() {
        Point point = new Point(rand.nextInt(dim.getWidth()), rand.nextInt(dim
                .getHeight()));
        assertNull(matrix.getAt(point));
        matrix.setAt(point, SOME_INTEGER);
        assertEquals(new Integer(SOME_INTEGER), matrix.getAt(point));
    }

    @Test
    public void testFillWith() {
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
