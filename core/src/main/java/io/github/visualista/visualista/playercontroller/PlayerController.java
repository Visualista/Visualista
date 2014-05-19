package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.view.IGetPlayerController;

import java.util.Iterator;
import java.util.List;

public class PlayerController implements IGetPlayerController{
    private Visualista visualista;
    private IPlayerView view;

    public PlayerController(final Visualista visualista,
            final IPlayerView view) {
        this.visualista = visualista;
        this.view = view;

    }
    
    public void actorClicked(Actor actor){
        actor.getActions();
    }

    @Override
    public void addDataToView() {
        if (view.getIsReady()){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
        }
        
    }

    @Override
    public void tileClicked(IGetActor actor) {
        List<IAction> actorActions = actor.getActions();
        Iterator<IAction> it = actorActions.iterator();
        while (it.hasNext()){
            performAction(it.next());
        }
        
    }
    
    private void performAction(IAction actionToPerform){
        
    }
}
