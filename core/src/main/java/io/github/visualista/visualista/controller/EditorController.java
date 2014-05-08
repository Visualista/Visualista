package io.github.visualista.visualista.controller;

import com.badlogic.gdx.Gdx;

import io.github.visualista.view.VisualistaView;
import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.core.model.Actor;


public class EditorController implements ViewEventListener {

    private Visualista visualista;
    private VisualistaView view;

    public EditorController(final Visualista visualista,
            final VisualistaView view) {
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
                visualista.addNewScene(true);
                view.addScene();
                break;
            case NEW_ACTOR:
                visualista.addNewActor();
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor)(event.getSourceObject()));
                break;
            case CHANGE_ACTOR_NAME:
                //TODO: Fix the Event as well as implementing actor name change
                break;

        }
    }

}
