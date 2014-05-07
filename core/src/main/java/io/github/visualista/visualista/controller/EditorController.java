package io.github.visualista.visualista.controller;

import com.badlogic.gdx.Gdx;

import io.github.visualista.view.VisualistaView;
import io.github.visualista.visualista.core.Visualista;

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
                final boolean addedNewScene = visualista.addNewScene();
                if (addedNewScene) {
                    view.addScene();
                }
                break;

        }
    }

}
