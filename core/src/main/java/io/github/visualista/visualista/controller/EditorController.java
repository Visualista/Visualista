package io.github.visualista.visualista.controller;

import com.badlogic.gdx.Gdx;

import io.github.visualista.view.IVisualistaView;
import io.github.visualista.view.VisualistaView;
import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.IGetScene;
import io.github.visualista.visualista.core.model.Scene;


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
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor)(event.getTargetObject()));
                break;
            case CHANGE_ACTOR_NAME:
                //TODO: Fix the Event as well as implementing actor name change
                break;
            case CHANGE_SCENE_NAME:
                visualista.changeCurrentSceneName((String)(event.getTargetObject()));
                view.updateScene(visualista.getCurrentNovel().getCurrentScene());
                break;
                
        }
    }

}
