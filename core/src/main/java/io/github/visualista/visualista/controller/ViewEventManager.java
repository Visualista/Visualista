package io.github.visualista.visualista.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewEventManager {
	private final List<ViewEventListener> eventListeners =
			new ArrayList<ViewEventListener>();

	public final synchronized void addEventListener(
			final ViewEventListener eventListener) {
		eventListeners.add(eventListener);
	}

	public final synchronized void removeEventListener(
			final ViewEventListener eventListener) {
		eventListeners.remove(eventListener);
	}

	public final synchronized void fireViewEvent() {
		final EditorViewEvent event = new EditorViewEvent(this,
				EditorViewEvent.Type.NEW_SCENE);
		final Iterator<ViewEventListener> eventIterator = eventListeners
				.iterator();
		while (eventIterator.hasNext()) {
			eventIterator.next().handleViewEvent(event);
		}
	}

}
