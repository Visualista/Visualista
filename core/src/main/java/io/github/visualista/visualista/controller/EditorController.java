package io.github.visualista.visualista.controller;

import java.io.File;

import com.badlogic.gdx.Gdx;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Image;
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
        IGetActor updatedActor;
        IGetScene updatedScene;
        IGetNovel updatedNovel;
        switch (event.getEventType()) {
            case NEW_SCENE:
                IGetScene newScene = visualista.addNewScene(true);
                view.addScene(newScene);
                break;
            case NEW_ACTOR:
                visualista.addNewActor();
                // TODO view needs to do something to update actor list
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor) (event.getTargetObject()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_ACTOR_NAME:
                updatedActor = visualista.changeActorName(
                        (Actor) (event.getTargetObject()),
                        (String) (event.getNewData()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_SCENE_NAME:
                updatedScene = visualista.changeSceneName(
                        (Scene) (event.getTargetObject()),
                        (String) (event.getNewData()));
                view.updateScene(updatedScene);
                break;
            case CHANGE_ACTOR_IMAGE:
                updatedActor = visualista.changeActorImage(
                        (Actor) (event.getTargetObject()),
                        (Image) (event.getNewData()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_SCENE_IMAGE:
                updatedScene = visualista.changeSceneImage(
                        (Scene) (event.getTargetObject()),
                        (Image) (event.getNewData()));
                view.updateScene(updatedScene);
                break;
            case CHANGE_SCENE_TEXT:
                updatedScene = visualista.changeSceneText(
                        (Scene) (event.getTargetObject()),
                        (String) (event.getNewData()));
                view.updateScene(updatedScene);
                break;
            case REMOVE_SCENE:
                updatedScene = visualista.removeScene((Scene) (event
                        .getTargetObject()));
                view.removeScene(updatedScene);
                break;
            case CHANGE_ACTIVE_SCENE:
                view.changeActiveScene((Scene) (event.getTargetObject()));
            case NEW_NOVEL:
                updatedNovel = visualista.createNewNovel();
                // TODO require any parameters?
                view.changeActiveNovel(updatedNovel);
            case VIEW_READY:
                fillViewFromModel();
                break;
            case FILE_OPEN:
                Gdx.app.log("File open", ""+event.getNewData());
                if (event.getNewData() instanceof File) {
                    updatedNovel = visualista.openNovel((File) event
                            .getNewData());
                    fillViewFromModel();
                }
                break;
            case FILE_SAVE:
                visualista.saveNovelIfNeeded();
                break;
            default:
                break;

        }
    }

}
