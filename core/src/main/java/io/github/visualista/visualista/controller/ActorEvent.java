package io.github.visualista.visualista.controller;

import java.util.EventObject;

import io.github.visualista.visualista.core.model.Actor;

public class ActorEvent extends EventObject {

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
