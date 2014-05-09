package io.github.visualista.visualista.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TileFactoryTest {

    @Test
    public void test() {
        TileFactory tileFactory = new TileFactory();
        Tile tile = tileFactory.createObject();
        assertNotNull(tile);
    }

}
