package io.github.visualista.visualista.model;

public class Tile {
    private Actor tileActor;

    public Tile(Actor tileActor) {
        this.tileActor = tileActor;
    }

    public Actor getActor() {
        return tileActor;
    }

    public void setActor(Actor tileActor) {
        this.tileActor = tileActor;
    }
}