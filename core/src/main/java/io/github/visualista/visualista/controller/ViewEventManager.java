package io.github.visualista.visualista.controller;

import io.github.visualista.visualista.controller.EditorViewEvent.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewEventManager {
    private final List<ViewEventListener> eventListeners = new ArrayList<ViewEventListener>();

    public final synchronized void addEventListener(
            final ViewEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public final synchronized void removeEventListener(
            final ViewEventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    public final synchronized void fireViewEvent(Object source, Type type) {
        fireViewEvent(source, type, null);
    }

    public final synchronized void fireViewEvent(Object source, Type type,
            Object targetObject) {
        fireViewEvent(source, type, targetObject, null);

    }

    public final synchronized void fireViewEvent(Object source, Type type,
            Object targetObject, Object newDataObject) {
        final EditorViewEvent event = new EditorViewEvent(source, type,
                targetObject,newDataObject);
        final Iterator<ViewEventListener> eventIterator = eventListeners
                .iterator();
        while (eventIterator.hasNext()) {
            eventIterator.next().handleViewEvent(event);
        }
    }

}
