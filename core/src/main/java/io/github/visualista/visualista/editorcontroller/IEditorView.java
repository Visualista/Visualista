package io.github.visualista.visualista.editorcontroller;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/** Interface responsible of communicating relevant methods to the EditorController.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */

public interface IEditorView {
    void addNewActor(IGetActor updatedActor);

    void addScene(IGetScene newScene);


    void changeActiveNovel(IGetNovel updatedNovel);

    void changeActiveScene(IGetScene scene);

    void clearModel();

    boolean getIsReady();

    void removeScene(IGetScene scene);
    void selectActor(IGetActor targetObject);

    void selectEditorTool(EditorTool tool);

    void updateActionList(IGetActor updatedActor);

    void updateActor(IGetActor updatedActor);

    void updateScene(IGetScene currentScene);

    void updateTile(Image updatedObject, IGetTile updatedTile);

    void setEventListener(ViewEventListener eventManager);
}
