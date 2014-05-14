package io.github.visualista.visualista.util;

import io.github.visualista.visualista.model.Actor;

public class PositionedActor {

    private final Point position;
    private final Actor actor;
    
    public PositionedActor(Point position, Actor actor){
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
