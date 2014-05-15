package io.github.visualista.visualista.controller;

import java.io.File;

import com.badlogic.gdx.Gdx;

import io.github.visualista.visualista.core.Visualista;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.Scene;
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
            view.changeActiveScene(visualista.getCurrentNovel().getScenes()
                    .get(0));
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
                visualista.addNewActor((Scene)event.getTargetObject());
                view.updateScene((IGetScene)event.getTargetObject());
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor) (event.getTargetObject()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_ACTOR_NAME:
                updatedActor = visualista.changeActorName(
                        (Actor) (event.getTargetObject()),
                        (String) (event.getExtraData()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_SCENE_NAME:
                updatedScene = visualista.changeSceneName(
                        (Scene) (event.getTargetObject()),
                        (String) (event.getExtraData()));
                Gdx.app.log("CHANGE_SCENE_NAME", "");
                view.updateScene(updatedScene);
                break;
            case CHANGE_ACTOR_IMAGE:
                updatedActor = visualista.changeActorImage(
                        (Actor) (event.getTargetObject()),
                        (Image) (event.getExtraData()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_SCENE_IMAGE:
                updatedScene = visualista.changeSceneImage((Scene) (event
                        .getTargetObject()),
                        new Image((File) (event.getExtraData())));
                view.updateScene(updatedScene);
                break;
            case CHANGE_SCENE_TEXT:
                updatedScene = visualista.changeSceneText(
                        (Scene) (event.getTargetObject()),
                        (String) (event.getExtraData()));
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
            case EDITOR_VIEW_READY:
                fillViewFromModel();
                break;
            case FILE_OPEN:
                Gdx.app.log("File open", "" + event.getExtraData());
                if (event.getExtraData() instanceof File) {
                    updatedNovel = visualista.openNovel((File) event
                            .getExtraData());
                    fillViewFromModel();
                }
                break;
            case FILE_SAVE:
                visualista.saveNovelIfNeeded();
                break;
            case CHANGE_NOVEL_NAME:
                ((Novel) (event.getSource())).setName((String) (event
                        .getExtraData()));
                // TODO update view?
                break;
            case CLICK_TILE:
                break;
            case SELECT_SCENE:
                view.changeActiveScene((IGetScene) event.getTargetObject());
                break;
            case PLAYER_VIEW_READY:
                break;
            default:
                break;

        }
    }

}
