package io.github.visualista.visualista.controller;

import java.util.EventObject;

public class EditorViewEvent extends EventObject {
    private static final long serialVersionUID = -8033356189055577154L;

    public enum Type {
        NEW_SCENE,
        NEW_ACTOR,
        SELECT_ACTOR,
        REMOVE_ACTOR,
        CHANGE_ACTOR_NAME,
        CHANGE_SCENE_NAME
    }

    private final Type eventType;
    private final Object sourceObject;

    public EditorViewEvent(final Object source, final Type eventType, final Object sourceObject) {
        super(source);
        this.eventType = eventType;
        this.sourceObject = sourceObject;
    }
    
    public EditorViewEvent(final Object source, final Type eventType){
        this(source, eventType, null);
    }

    public final Type getEventType() {
        return eventType;
    }
    
    public final Object getSourceObject() {
        return sourceObject;
    }
}
