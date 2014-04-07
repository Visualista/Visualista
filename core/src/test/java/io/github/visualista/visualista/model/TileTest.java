package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Tile;

import org.junit.Test;

public class TileTest {

    @Test
    public void test() {
        Tile tile = new Tile(new Actor());
        assertThat(tile.getActor(),notNullValue());
    }
    

}
