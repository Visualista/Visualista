package io.github.visualista.visualista.editorcontroller;

import java.util.EventObject;

public class EditorViewEvent extends EventObject {
    private static final long serialVersionUID = -8033356189055577154L;

    public enum Type {
        NEW_SCENE, NEW_ACTOR, REMOVE_ACTOR, CHANGE_ACTOR_NAME,
        CHANGE_SCENE_NAME, CHANGE_ACTOR_IMAGE, CHANGE_SCENE_IMAGE,
        CHANGE_SCENE_TEXT, REMOVE_SCENE, CHANGE_ACTIVE_SCENE,
        NEW_NOVEL, VIEW_READY, FILE_OPEN, FILE_SAVE, CHANGE_NOVEL_NAME,
        CLICK_TILE,SELECT_SCENE, SELECT_ACTOR
    }

    private final Type eventType;
    private final Object targetObject;
    private final Object extraData;

    public EditorViewEvent(final Object source, final Type eventType,
            final Object targetObject, final Object extraData) {
        super(source);
        this.eventType = eventType;
        this.targetObject = targetObject;
        this.extraData = extraData;
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

    public final Object getExtraData() {
        return extraData;
    }
}
