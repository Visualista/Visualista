package io.github.visualista.visualista.controller;

import java.util.EventObject;

public class EditorViewEvent extends EventObject {
    private static final long serialVersionUID = -8033356189055577154L;

    public enum Type {
        NEW_SCENE, NEW_ACTOR, REMOVE_ACTOR, CHANGE_ACTOR_NAME, CHANGE_SCENE_NAME, CHANGE_ACTOR_IMAGE, CHANGE_SCENE_IMAGE, CHANGE_SCENE_TEXT, REMOVE_SCENE
    }

    private final Type eventType;
    private final Object targetObject;
    private final Object newDataObject;

    public EditorViewEvent(final Object source, final Type eventType,
            final Object targetObject, final Object newDataObject) {
        super(source);
        this.eventType = eventType;
        this.targetObject = targetObject;
        this.newDataObject = newDataObject;
    }

    public EditorViewEvent(final Object source, final Type eventType,
            final Object targetObject) {
        this(source, eventType, targetObject, null);
    }

    public EditorViewEvent(final Object source, final Type eventType) {
        this(source, eventType, null);
    }

    public final Type getEventType() {
        return eventType;
    }

    public final Object getTargetObject() {
        return targetObject;
    }

    public final Object getNewData() {
        return newDataObject;
    }
}
