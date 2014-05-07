package io.github.visualista.visualista.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewEventManager {
	private final List<ViewEventListener> eventListeners = new ArrayList<ViewEventListener>();
	
	public synchronized void addEventListener(ViewEventListener eventListener) {
		eventListeners.add(eventListener);
	}

	public synchronized void removeEventListener(ViewEventListener eventListener) {
		eventListeners.remove(eventListener);
	}
	
	public synchronized void fireViewEvent() {
		ViewEvent event = new ViewEvent(this);
		Iterator<ViewEventListener> eventIterator = eventListeners.iterator();
		while (eventIterator.hasNext()) {
			eventIterator.next().handleViewEvent(event);
		}
	}

}
