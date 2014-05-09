package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.IObjectCreator;

public class TileFactory implements IObjectCreator<Tile> {

    public TileFactory() {
    }

    public Tile createTile() {
        return new Tile(Actor.EMPTY_ACTOR);
    }

    @Override
    public Tile createObject() {
        return createTile();
    }
}
