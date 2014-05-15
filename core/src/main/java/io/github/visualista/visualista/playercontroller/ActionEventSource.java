package io.github.visualista.visualista.playercontroller;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionEventSource {
    protected final List<ActionEventListener> actorEventListeners = new ArrayList<ActionEventListener>();

    public synchronized void addEventListener(ActionEventListener actionEventListener) {
        actorEventListeners.add(actionEventListener);
    }

    public synchronized void removeEventListener(ActionEventListener actionEventListener) {
        actorEventListeners.remove(actionEventListener);
    }

    public abstract void fireActionEvent();
}
