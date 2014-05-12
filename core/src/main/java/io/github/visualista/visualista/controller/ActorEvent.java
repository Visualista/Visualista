package io.github.visualista.visualista.controller;

import java.util.EventObject;

import io.github.visualista.visualista.model.Actor;

public class ActorEvent extends EventObject {
    private static final long serialVersionUID = 1968131011722690013L;
    
    public ActorEvent(Object source) {
        super(source);
    }

    public Object getSource() {
        return source;
    }

    public Actor getActor() {
        if (source.getClass() == Actor.class) {
            return (Actor) source;
        }
        return null;
    }
}
