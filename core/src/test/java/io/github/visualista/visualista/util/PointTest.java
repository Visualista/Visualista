package io.github.visualista.visualista.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

public class PointTest {
    private Point point;
    private int width;
    private int y;

    @Before
    public void setUp() throws Exception {
        Random rand = new Random();
        width = rand.nextInt();
        y = rand.nextInt();
        point = new Point(width, y);
    }

    @Test
    public void testGetX() {
        assertEquals(width, point.getX());
    }

    @Test
    public void testGetY() {
        assertEquals(y, point.getY());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Point.class).verify();
    }

    @Test
    public void testToString() {
        assertTrue(point.toString().contains("" + width));
        assertTrue(point.toString().contains("" + y));
    }
}
