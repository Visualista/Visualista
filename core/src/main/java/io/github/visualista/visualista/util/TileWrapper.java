package io.github.visualista.visualista.util;

import io.github.visualista.visualista.model.Actor;

public class TileWrapper {

    private final Point position;
    private final Actor actor;
    
    public TileWrapper(Point position, Actor actor){
        this.actor = actor;
        this.position = position;
    }
    
    public Point getPosition(){
        return position;
    }
    
    public Actor getActor(){
        return actor;
    }
}
