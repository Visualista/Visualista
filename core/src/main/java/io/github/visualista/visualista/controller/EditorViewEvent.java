package io.github.visualista.visualista.controller;

import java.util.EventObject;

public class EditorViewEvent extends EventObject {
	private static final long serialVersionUID = -8033356189055577154L;

	enum Type{
		NEW_SCENE
	}

	private final Type eventType;

	public EditorViewEvent(final Object source, final Type eventType) {
		super(source);
		this.eventType = eventType;
	}

	public final Type getEventType() {
		return eventType;
	}

}
