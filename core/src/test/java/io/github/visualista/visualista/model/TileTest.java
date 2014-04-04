package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Tile;

import org.junit.Test;

public class TileTest {

    @Test
    public void test() {
        Tile tile = new Tile(new Actor());
        assertNotNull(tile);
    }

    @Test
    public void testGetActor() {
        Tile tile = new Tile(new Actor());
        assertNotEquals(-1,tile.getActorId());
    }

    @Test
    public void testSetActor() {
        Tile tile = new Tile(new Actor());
        Actor actor = new Actor(5);
        tile.setActor(actor);
        assertEquals(tile.getActorId(), 5);
    }

}
