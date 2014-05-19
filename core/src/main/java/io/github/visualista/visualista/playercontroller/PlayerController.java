package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.PositionedActor;
import io.github.visualista.visualista.model.Scene;
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
        view.addController(this);
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
            Object objectsToModify = it.next().getActionData();
            changeData(objectsToModify);
        }
        
    }
    
    private boolean changeData(Object dataToModify){
        if (dataToModify instanceof String){
            return changeText((String) dataToModify);
        } else if (dataToModify instanceof Scene){
            return changeScene((Scene) dataToModify);
        } else if (dataToModify instanceof PositionedActor){
            return changeActor((PositionedActor) dataToModify);
        } else {
            return false;
        }
    }
    
    private boolean changeScene(Scene newScene){
        visualista.getCurrentNovel().setCurrentScene(newScene);
        return view.getIsReady();
    }
    
    private boolean changeText(String newText){
        visualista.getCurrentNovel().getCurrentScene().setStoryText(newText);
        return view.getIsReady();
    }
    
    private boolean changeActor(PositionedActor newActor){
        visualista.getCurrentNovel().getCurrentScene().getGrid().getAt(newActor.getPosition()).setActor(newActor.getActor());
        return view.getIsReady();
    }
}
