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

        fillViewFromModel(visualista.getCurrentNovel());

    }

    private void addEventHandlersToView() {
        view.setEventListener(this);
    }

    private void fillViewFromModel(final Novel model) {
        if (view.getIsReady()) {
            view.clearModel();
            for (Scene scene : model.getScenes()) {
                view.addScene(scene);
            }
            view.changeActiveScene(model.getScenes()
                    .get(0));
        }

    }

    @Override
    public void addSetActorAction(final IGetActor actor,
            final PositionedActor positionedActor) {
        actor.getActions().add(new SetActorAction(positionedActor));
        view.updateActionList(actor);

    }

    @Override
    public void selectTile(final IGetTile tile) {
        view.selectActor(tile.getActor());

    }

    @Override
    public void addSetSceneAction(final IGetActor actor,
            final IGetScene selected) {
        actor.getActions().add(new SetSceneAction((Scene) selected));
        view.updateActionList(actor);

    }

    @Override
    public void selectActor(final IGetActor selected) {
        view.selectActor(selected);

    }

    @Override
    public void newActor(final IGetScene activeScene) {
        IGetActor updatedActor = visualista.addNewActor((Scene) activeScene);
        view.addNewActor(updatedActor);

    }

    @Override
    public void selectEditorTool(final EditorTool arrow) {
        view.selectEditorTool(arrow);

    }

    @Override
    public void removeActor(final IGetScene activeScene) {
        // visualista.removeActor((Actor) event.getTargetObject());
        // TODO FIX!
    }

    @Override
    public void fileOpen(final File selectedFile) {
        Novel updatedNovel = visualista.openNovel(selectedFile);
        fillViewFromModel(updatedNovel);
    }

    @Override
    public void fileSave(final File file) {
        File fileToSave = file;
        if (!file.getName().toLowerCase().endsWith(VISUALISTA_FILE_FORMAT)) {
            fileToSave = new File(file.getAbsoluteFile()
                    + VISUALISTA_FILE_FORMAT);
        }
        visualista.saveNovel(fileToSave);

    }

    @Override
    public void changeSceneImage(final IGetScene activeScene,
            final File selectedFile) {
        IGetScene updatedScene = visualista.changeSceneImage(
                (Scene) activeScene,
                new Image(FileImporter.importAndGetFile(selectedFile)));
        view.updateScene(updatedScene);

    }

    @Override
    public void changeSceneText(final IGetScene activeScene, final String text) {
        IGetScene updatedScene = visualista.changeSceneText(
                (Scene) activeScene, text);
        view.updateScene(updatedScene);

    }

    @Override
    public void changeActorName(final IGetActor selectedActor,
            final String text) {
        Actor updatedActor = visualista.changeActorName((Actor) selectedActor,
                text);
        view.updateActor(updatedActor);
    }

    @Override
    public void addSetSceneTextAction(final IGetActor actor,
            final String text) {
        actor.getActions().add(new SetSceneTextAction(text));
        view.updateActionList(actor);

    }

    @Override
    public void selectScene(final IGetScene scene) {
        view.changeActiveScene(scene);

    }

    @Override
    public void changeSceneName(final IGetScene scene, final String newName) {
        IGetScene updatedScene = visualista.changeSceneName((Scene) scene,
                newName);
        view.updateScene(updatedScene);
        view.changeActiveScene(updatedScene);

    }

    @Override
    public void viewIsReady() {
        fillViewFromModel(visualista.getCurrentNovel());

    }

    @Override
    public void newScene() {
        IGetScene newScene = visualista.addNewScene(true);
        view.addScene(newScene);

    }

    @Override
    public void changeActorImage(final IGetActor selectedActor,
            final File selectedFile) {
        IGetActor updatedActor = visualista.changeActorImage(
                (Actor) selectedActor,
                new Image(FileImporter.importAndGetFile(selectedFile)));
        view.updateActor(updatedActor);

    }

    @Override
    public void tileSetActor(final com.badlogic.gdx.scenes.scene2d.ui.Image image,
            final IGetTile tile, final IGetActor selectedActor) {
        ((Tile) tile).setActor((Actor) selectedActor);
        view.updateTile(image, tile);

    }

}
