package io.github.visualista.visualista.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionEventManeger {
    private final List<ActionEventListener> eventListeners = new ArrayList<ActionEventListener>();
    
    public final synchronized void addEventListener(final ActionEventListener ael){
        eventListeners.add(ael);
    }
    
    public final synchronized void removeEventListener(final ActionEventListener ael){
        eventListeners.remove(ael);
    }
    
    public final synchronized void fireActionEvent(Object source, ActionEventType type, Object newData){
        final ActionEvent actionEvent = new ActionEvent(source, type, newData);
        final Iterator<ActionEventListener> it = eventListeners.iterator();
        while (it.hasNext()){
            it.next().handleActionEvent(actionEvent);
        }
    }
}
