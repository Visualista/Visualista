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

    private final VisualistaPlayer visualista;
    private final IPlayerView view;
    private final IFilePicker filePicker;
    private Scene currentSceneToChange;

    public PlayerController(final VisualistaPlayer visualista,
            final IPlayerView view, IFilePicker filePicker) {
        this.visualista = visualista;
        this.view = view;
        this.filePicker = filePicker;
        view.setController(this);
    }

    public void updateView() {
        if (visualista.getCurrentNovel() != null){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
            view.removeFileLoadListeners();
        }

    }

    @Override
    public void addDataToView() {
        if (view.getIsReady()){
            view.updateScene(visualista.getCurrentNovel().getCurrentScene());
        }

    }

    @Override
    public void openNovelFile(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Choose Visual Novel (*.vis)", "vis");
        filePicker.openFileDialog(new FilePickerListener() {

            @Override
            public void fileOpened(File selectedFile) {
                visualista.openNovel(selectedFile);
                updateView();
            }

            @Override
            public void fileSaved(File selectedFile) {
                // Unreachable as it is
            }
        }, filter);

    }

    @Override
    public void tileClicked(IGetActor actor) {
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

    private boolean changeData(Object dataToModify){
        Gdx.app.log("Wat", "Calling Action");
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

    private boolean changeScene(Scene newScene){
        Gdx.app.log("Change Scene", newScene.toString());
        visualista.getCurrentNovel().setCurrentScene(newScene);
        return view.getIsReady();
    }

    private boolean changeText(String newText){
        Gdx.app.log("Change Text", "In " + currentSceneToChange.toString());
        currentSceneToChange.setStoryText(newText);
        return view.getIsReady();
    }

    private boolean changeActor(PositionedActor newActor){
        currentSceneToChange.getGrid().getAt(
                newActor.getPosition()).setActor(newActor.getActor());
        return view.getIsReady();
    }
}
