package io.github.visualista.visualista.editorcontroller;

import io.github.visualista.visualista.core.VisualistaEditor;
import io.github.visualista.visualista.io.FileImporter;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.model.Image;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.PositionedActor;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.SetActorAction;
import io.github.visualista.visualista.model.SetSceneAction;
import io.github.visualista.visualista.model.SetSceneTextAction;
import io.github.visualista.visualista.model.Tile;

import java.io.File;

/**
 * Controller responsible for handling events from the Editor view. Also sends
 * data from the model to the view.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 * 
 */
public class EditorController implements ViewEventListener {

    private static final String VISUALISTA_FILE_FORMAT = ".vis";
    private final IEditorView view;
    private final VisualistaEditor visualista;

    public EditorController(final VisualistaEditor visualista,
            final IEditorView view) {
        this.visualista = visualista;
        this.view = view;
        addEventHandlersToView();

        fillViewFromModel();

    }

    private void addEventHandlersToView() {
        view.setEventListener(this);
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

    @Override
    public void ADD_SET_ACTOR_ACTION(final IGetActor actor,
            final PositionedActor positionedActor) {
        actor.getActions().add(new SetActorAction(positionedActor));
        view.updateActionList(actor);

    }

    @Override
    public void SELECT_TILE(final IGetTile tile) {
        view.selectActor(tile.getActor());

    }

    @Override
    public void ADD_SET_SCENE_ACTION(final IGetActor actor,
            final IGetScene selected) {
        actor.getActions().add(new SetSceneAction((Scene) selected));
        view.updateActionList(actor);

    }

    @Override
    public void SELECT_ACTOR(final IGetActor selected) {
        view.selectActor(selected);

    }

    @Override
    public void NEW_ACTOR(final IGetScene activeScene) {
        IGetActor updatedActor = visualista.addNewActor((Scene) activeScene);
        view.addNewActor(updatedActor);

    }

    @Override
    public void SELECT_EDITOR_TOOl(final EditorTool arrow) {
        view.selectEditorTool(arrow);

    }

    @Override
    public void REMOVE_ACTOR(final IGetScene activeScene) {
        // visualista.removeActor((Actor) event.getTargetObject());
        // TODO FIX!
    }

    @Override
    public void FILE_OPEN(final File selectedFile) {
        Novel updatedNovel = visualista.openNovel(selectedFile);
        fillViewFromModel();
    }

    @Override
    public void FILE_SAVE(final File file) {
        File fileToSave = file;
        if (!file.getName().toLowerCase().endsWith(VISUALISTA_FILE_FORMAT)) {
            fileToSave = new File(file.getAbsoluteFile()
                    + VISUALISTA_FILE_FORMAT);
        }
        visualista.saveNovel(fileToSave);

    }

    @Override
    public void CHANGE_SCENE_IMAGE(final IGetScene activeScene,
            final File selectedFile) {
        IGetScene updatedScene = visualista.changeSceneImage(
                (Scene) activeScene,
                new Image(FileImporter.importAndGetFile(selectedFile)));
        view.updateScene(updatedScene);

    }

    @Override
    public void CHANGE_SCENE_TEXT(final IGetScene activeScene, final String text) {
        IGetScene updatedScene = visualista.changeSceneText(
                (Scene) activeScene, text);
        view.updateScene(updatedScene);

    }

    @Override
    public void CHANGE_ACTOR_NAME(final IGetActor selectedActor,
            final String text) {
        Actor updatedActor = visualista.changeActorName((Actor) selectedActor,
                text);
        view.updateActor(updatedActor);
    }

    @Override
    public void ADD_SET_SCENE_TEXT_ACTION(final IGetActor actor,
            final String text) {
        actor.getActions().add(new SetSceneTextAction(text));
        view.updateActionList(actor);

    }

    @Override
    public void SELECT_SCENE(final IGetScene scene) {
        view.changeActiveScene(scene);

    }

    @Override
    public void CHANGE_SCENE_NAME(final IGetScene scene, final String newName) {
        IGetScene updatedScene = visualista.changeSceneName((Scene) scene,
                newName);
        view.updateScene(updatedScene);
        view.changeActiveScene(updatedScene);

    }

    @Override
    public void VIEW_READY() {
        fillViewFromModel();

    }

    @Override
    public void NEW_SCENE() {
        IGetScene newScene = visualista.addNewScene(true);
        view.addScene(newScene);

    }

    @Override
    public void CHANGE_ACTOR_IMAGE(final IGetActor selectedActor,
            final File selectedFile) {
        IGetActor updatedActor = visualista.changeActorImage(
                (Actor) selectedActor,
                new Image(FileImporter.importAndGetFile(selectedFile)));
        view.updateActor(updatedActor);

    }

    @Override
    public void TILE_SET_ACTOR(final com.badlogic.gdx.scenes.scene2d.ui.Image image,
            final IGetTile tile, final IGetActor selectedActor) {
        ((Tile) tile).setActor((Actor) selectedActor);
        view.updateTile(image, tile);

    }

}
