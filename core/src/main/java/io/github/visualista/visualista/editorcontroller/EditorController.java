package io.github.visualista.visualista.editorcontroller;

import java.io.File;

import com.badlogic.gdx.Gdx;

import io.github.visualista.visualista.core.VisualistaEditor;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.SetSceneAction;
import io.github.visualista.visualista.model.SetSceneTextAction;
import io.github.visualista.visualista.model.Tile;
import io.github.visualista.visualista.view.EditorView;

/** Controller responsible for handling events from the Editor
 * view. Also sends data from the model to the view.
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 *
 */
public class EditorController implements ViewEventListener {

    private VisualistaEditor visualista;
    private IEditorView view;

    public EditorController(final VisualistaEditor visualista,
            final IEditorView view) {
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
                updatedActor = visualista.addNewActor((Scene) event
                        .getTargetObject());
                view.addActor(updatedActor);
                break;
            case REMOVE_ACTOR:
                visualista.removeActor((Actor) (event.getTargetObject()));
                // TODO view needs to do something to update actor list
                break;
            case CHANGE_ACTOR_NAME:
                updatedActor = visualista.changeActorName(
                        (Actor) (event.getTargetObject()),
                        (String) (event.getExtraData()));
                view.updateActor(updatedActor);
                break;
            case CHANGE_SCENE_NAME:
                updatedScene = visualista.changeSceneName(
                        (Scene) (event.getTargetObject()),
                        (String) (event.getExtraData()));
                Gdx.app.log("CHANGE_SCENE_NAME", "");
                view.updateScene(updatedScene);
                view.changeActiveScene(updatedScene);
                break;
            case CHANGE_ACTOR_IMAGE:
                updatedActor = visualista.changeActorImage((Actor) (event
                        .getTargetObject()),
                        new Image((File) (event.getExtraData())));
                view.updateActor(updatedActor);
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
            case VIEW_READY:
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
                File file = (File) event.getExtraData();
                if (!file.getName().toLowerCase().endsWith(".vis")) {
                    file = new File(file.getAbsoluteFile() + ".vis");
                }
                visualista.saveNovel(file);
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
            case SELECT_ACTOR:
                view.selectActor((IGetActor) event.getTargetObject());
                break;
            case TILE_SET_ACTOR:
                Tile updatedTile = (Tile) event.getTargetObject();
                updatedTile.setActor((Actor) event.getExtraData());
                view.updateTile(event.getSource(), updatedTile);
                break;
            case SELECT_TILE:
                view.selectActor(((Tile) (event.getTargetObject())).getActor());
                break;
            case ADD_SET_SCENE_TEXT_ACTION:
                updatedActor = (Actor) (event.getTargetObject());
                updatedActor.getActions().add(
                        new SetSceneTextAction(
                                ((String) (event.getExtraData()))));
                view.updateActionList(updatedActor);
                break;
            case ADD_SET_SCENE_ACTION:
                updatedActor = (Actor) (event.getTargetObject());
                Scene scene = (Scene) event.getExtraData();
                updatedActor.getActions().add(new SetSceneAction(scene));
                view.updateActionList(updatedActor);
                break;
            default:
                break;

        }
    }

}
