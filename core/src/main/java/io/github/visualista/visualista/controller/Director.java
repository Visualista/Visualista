package io.github.visualista.visualista.controller;

import java.awt.event.ActionEvent;

import io.github.visualista.visualista.core.model.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Director implements ActorListener {

    private final List<IPlayActor> controlledActors;

    public Director() {
        controlledActors = new ArrayList<IPlayActor>();
    }

    public void updateListeners(List<IPlayActor> newActors) {
        controlledActors.clear();
        controlledActors.addAll(newActors);
        Iterator<IPlayActor> it = controlledActors.iterator();
        while (it.hasNext()) {
            ((Actor) it.next()).addEventListener(this);
            System.out.println("Adding listener to Actor "
                    + it.next().toString());
        }
    }

    @Override
    public void actorCalled(ActorEvent ae) {
        ae.getActor().getActions();

    }

}
