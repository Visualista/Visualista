package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import io.github.visualista.visualista.model.Actor;

import org.junit.Before;
import org.junit.Test;

public class TileTest {

    private TileFactory tileFactory;
    private Tile tile;

    @Before
    public void setUp() throws Exception {
        tileFactory = new TileFactory();
        tile = tileFactory.createTile();
    }

    @Test
    public void test() {
        assertThat(tile, notNullValue());
        assertThat(tile.getActor(), notNullValue());
    }

    @Test
    public void testSetAndGetActor() {
        Actor actor = new ActorFactory().createActor();
        tile.setActor(actor);
        assertThat(actor, equalTo(tile.getActor()));
    }

}
