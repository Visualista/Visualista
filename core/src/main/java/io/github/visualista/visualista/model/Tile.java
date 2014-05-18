package io.github.visualista.visualista.model;

public final class Tile implements IGetTile {
    private Actor tileActor;

    public Tile(final Actor tileActor) {
        this.tileActor = tileActor;
    }

    public Actor getActor() {
        return tileActor;
    }

    public void setActor(final Actor actor) {
        this.tileActor = actor;
    }
}
