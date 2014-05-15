package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.model.*;
import io.github.visualista.visualista.model.IGetActor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Director implements ActorListener {

    private final List<IGetActor> controlledActors;

    public Director() {
        controlledActors = new ArrayList<IGetActor>();
    }

    public void updateListeners(List<IGetActor> newActors) {
        controlledActors.clear();
        controlledActors.addAll(newActors);
        Iterator<IGetActor> it = controlledActors.iterator();
        while (it.hasNext()) {
            ((Actor) it.next()).addEventListener(this);
            System.out.println("Adding listener to Actor "
                    + it.next().toString());
        }
    }

    @Override
    public void actorCalled(ActorEvent ae) {
        List<IPlayAction> actorActions = new ArrayList<IPlayAction>(ae.getActor().getActions());
        Iterator<IPlayAction> it = actorActions.iterator();
        while (it.hasNext()){
            it.next().callAction();
        }
    }
    
    public void addListener(IGetActor newActor){
        if (!controlledActors.contains(newActor)){
            controlledActors.add(newActor);
            ((Actor)newActor).addEventListener(this);
        }
    }

}
