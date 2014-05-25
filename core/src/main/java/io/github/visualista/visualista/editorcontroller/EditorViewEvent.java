package io.github.visualista.visualista.editorcontroller;

import java.util.EventObject;

/** An EventObject tuned to the events that can happen in the Editor View.
 * Carries work data.
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 *
 */

public class EditorViewEvent extends EventObject {
    public enum Type {
        ADD_SET_ACTOR_ACTION, ADD_SET_SCENE_ACTION, ADD_SET_SCENE_TEXT_ACTION, CHANGE_ACTIVE_SCENE,
        CHANGE_ACTOR_IMAGE, CHANGE_ACTOR_NAME, CHANGE_NOVEL_NAME,
        CHANGE_SCENE_IMAGE, CHANGE_SCENE_NAME, CHANGE_SCENE_TEXT,
        CLICK_TILE, FILE_OPEN, FILE_SAVE, NEW_ACTOR, NEW_NOVEL,
        NEW_SCENE,REMOVE_ACTOR, REMOVE_SCENE, SELECT_ACTOR,
        SELECT_EDITOR_TOOl, SELECT_SCENE, SELECT_TILE,
        TILE_SET_ACTOR, VIEW_READY
    }

    private static final long serialVersionUID = -8033356189055577154L;

    private final Type eventType;
    private final Object extraData;
    private final Object targetObject;

    public EditorViewEvent(final Object source, final Type eventType) {
        this(source, eventType, null);
    }

    public EditorViewEvent(final Object source, final Type eventType,
            final Object targetObject) {
        this(source, eventType, targetObject, null);
    }

    public EditorViewEvent(final Object source, final Type eventType,
            final Object targetObject, final Object extraData) {
        super(source);
        this.eventType = eventType;
        this.targetObject = targetObject;
        this.extraData = extraData;
    }

    public final Type getEventType() {
        return eventType;
    }

    public final Object getExtraData() {
        return extraData;
    }

    public final Object getTargetObject() {
        return targetObject;
    }
}
