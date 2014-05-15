package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent;
import io.github.visualista.visualista.editorcontroller.IVisualistaView;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.SetActorAction;
import io.github.visualista.visualista.model.SetSceneAction;
import io.github.visualista.visualista.util.Point;

import java.io.File;

import com.badlogic.gdx.Gdx;

public class PlayerController implements ViewEventListener, ActionEventListener{
    private Visualista visualista;
    private IVisualistaView view;

    public PlayerController(final Visualista visualista,
            final IVisualistaView view) {
        this.visualista = visualista;
        this.view = view;
        addEventHandlersToView();

        fillViewFromModel();

    }

    private void fillViewFromModel() {
        if (view.getIsReady()) {
            view.clearModel();
            for (Scene scene : visualista.getCurrentNovel().getScenes()) {
                view.addScene(scene);
            }
        }

    }

    private void addEventHandlersToView() {
        view.addViewEventListener(this);
    }

    @Override
    public final void handleViewEvent(final EditorViewEvent event) {
        switch (event.getEventType()) {
            case CLICK_TILE:
                Point tilePoint = ((Point)event.getExtraData());
                Actor selectedActor = visualista.getActorFromPosition(tilePoint);
                selectedActor.playActor();
                break;
            default:
                break;

        }
    }

    @Override
    public void handleActionEvent(ActionEvent ae) {
        switch (ae.getAeType()){
            case SET_ACTOR:
                break;
            case SET_SCENE:
                break;
            case SET_TEXT:
                
                break;
            default:
                break;
        }
        
    }
}
