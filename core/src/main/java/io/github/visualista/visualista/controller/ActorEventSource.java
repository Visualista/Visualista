package io.github.visualista.visualista.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActorEventSource {
    private final List<ActorListener> actorEventListeners = new ArrayList<ActorListener>();

    public synchronized void addEventListener(ActorListener actorListener) {
        actorEventListeners.add(actorListener);
    }

    public synchronized void removeEventListener(ActorListener actorListener) {
        actorEventListeners.remove(actorListener);
    }

    protected synchronized void fireActorEvent() {
        ActorEvent event = new ActorEvent(this);
        Iterator<ActorListener> eventIterator = actorEventListeners.iterator();
        while (eventIterator.hasNext()) {
            eventIterator.next().actorCalled(event);
        }
    }
}
