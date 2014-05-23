package io.github.visualista.visualista.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImageTest {

    private Image image;

    @Before
    public void setUp(){
        image = new Image(null);
    }

    @Test
    public void testGetFile(){
        assertNull(image.getFileHandle());
    }
}
