package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.core.VisualistaPlayer;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.PositionedActor;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.view.IGetPlayerController;
import io.github.visualista.visualista.view.IPlayerView;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;

/** The controller for the Player View, data gatherer from the Player Model.
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 *
 */
public class PlayerController implements IGetPlayerController{

    private Scene currentSceneToChange;
    private final IFilePicker filePicker;
    private final IPlayerView view;
    private final VisualistaPlayer visualista;

    public PlayerController(final VisualistaPlayer visualista,
            final IPlayerView view, final IFilePicker filePicker) {
        this.visualista = visualista;
        this.view = view;
        this.filePicker = filePicker;
        view.setController(this);
    }

    @Override
    public void addDataToView() {
        if (view.getIsReady()){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
        }

    }

    private boolean changeActor(final PositionedActor newActor){
        currentSceneToChange.getTileAt(
                newActor.getPosition()).setActor(newActor.getActor());
        return view.getIsReady();
    }

    private boolean changeData(final Object dataToModify){
        if (dataToModify instanceof String){
            return changeText((String) dataToModify);
        } else if (dataToModify instanceof Scene){
            return changeScene((Scene) dataToModify);
        } else if (dataToModify instanceof PositionedActor){
            return changeActor((PositionedActor) dataToModify);
        } else {
            return false;
        }
    }

    private boolean changeScene(final Scene newScene){
        Gdx.app.log("Change Scene", newScene.toString());
        visualista.getCurrentNovel().setCurrentScene(newScene);
        return view.getIsReady();
    }

    private boolean changeText(final String newText){
        Gdx.app.log("Change Text", "In " + currentSceneToChange.toString());
        currentSceneToChange.setStoryText(newText);
        return view.getIsReady();
    }

    @Override
    public void openNovelFile(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Choose Visual Novel (*.vis)", "vis");
        filePicker.openFileDialog(new FilePickerListener() {

            @Override
            public void fileOpened(final File selectedFile) {
                visualista.openNovel(selectedFile);
                updateView();
            }

            @Override
            public void fileSaved(final File selectedFile) {
                // Unreachable as it is
            }
        }, filter);

    }

    @Override
    public void tileClicked(final IGetActor actor) {
        currentSceneToChange = visualista.getCurrentNovel().getCurrentScene();
        boolean needUpdate = false;
        List<IAction> actorActions = actor.getActions();
        Iterator<IAction> it = actorActions.iterator();
        while (it.hasNext()){
            IAction actionToCall = it.next();
            Object objectsToModify = actionToCall.getActionData();
            needUpdate = changeData(objectsToModify) || needUpdate;
            Gdx.app.log("Action", actionToCall.toString());
        }
        if (needUpdate){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
        }

    }

    public void updateView() {
        if (visualista.getCurrentNovel() != null){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
            view.removeFileLoadListeners();
        }

    }
}
