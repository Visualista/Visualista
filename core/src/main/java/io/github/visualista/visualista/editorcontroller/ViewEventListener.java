package io.github.visualista.visualista.editorcontroller;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.model.PositionedActor;

import java.io.File;

/** Interface responsible of communicating relevant methods to the
 * viewEventSource
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 *
 */
public interface ViewEventListener {

    void addSetActorAction(IGetActor actor, PositionedActor positionedActor);

    void selectTile(IGetTile tile);

    void addSetSceneAction(IGetActor actor, IGetScene selected);

    void selectActor(IGetActor selected);

    void newActor(IGetScene activeScene);

    void selectEditorTool(EditorTool arrow);

    void removeActor(IGetScene activeScene);

    void fileOpen(File selectedFile);

    void fileSave(File selectedFile);

    void changeSceneImage(IGetScene activeScene, File selectedFile);

    void changeSceneText(IGetScene activeScene, String text);

    void changeActorName(IGetActor selectedActor, String text);

    void addSetSceneTextAction(IGetActor actor, String text);

    void selectScene(IGetScene value);

    void changeSceneName(IGetScene value, String newName);

    void viewIsReady();

    void newScene();

    void changeActorImage(IGetActor selectedActor, File selectedFile);

    void tileSetActor(com.badlogic.gdx.scenes.scene2d.ui.Image image, IGetTile tile, IGetActor selectedActor);




}

