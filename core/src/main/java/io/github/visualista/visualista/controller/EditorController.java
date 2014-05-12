package io.github.visualista.visualista.controller;

import com.badlogic.gdx.Gdx;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.view.IVisualistaView;
import io.github.visualista.visualista.view.VisualistaView;


public class EditorController implements ViewEventListener {

    private Visualista visualista;
    private IVisualistaView view;

    public EditorController(final Visualista visualista,
            final IVisualistaView view) {
        this.visualista = visualista;
        this.view = view;
        addEventHandlersToView();
    }

    private void addEventHandlersToView() {
        view.addViewEventListener(this);
    }

    @Override
    public final void handleViewEvent(final EditorViewEvent event) {
        switch (event.getEventType()) {
            case NEW_SCENE:
                IGetScene newScene = visualista.addNewScene(true);
                view.addScene(newScene);
                break;
            case NEW_ACTOR:
                visualista.addNewActor();
              //TODO view needs to do something to update actor list
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor)(event.getTargetObject()));
              //TODO view needs to do something to update actor list
                break;
            case CHANGE_ACTOR_NAME:
                Actor updatedActor = visualista.changeActorName(
                    (Actor)(event.getTargetObject()),(String)(event.getNewData()));
                //TODO view needs to do something to update actor list
                break;
            case CHANGE_SCENE_NAME:
                Scene updatedScene = visualista.changeSceneName(
                    (Scene)(event.getTargetObject()),(String)(event.getNewData()));
                view.updateScene(updatedScene);
                break;
                
        }
    }

}
