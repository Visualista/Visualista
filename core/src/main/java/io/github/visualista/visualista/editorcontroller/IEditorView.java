package io.github.visualista.visualista.editorcontroller;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.view.EditorTool;

/** Interface responsible of communicating relevant methods to the EditorController.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */

public interface IEditorView {
    void addNewActor(IGetActor updatedActor);

    void addScene(IGetScene newScene);

    void addViewEventListener(ViewEventListener eventListener);

    void changeActiveNovel(IGetNovel updatedNovel);

    void changeActiveScene(IGetScene scene);

    void clearModel();

    boolean getIsReady();

    void removeScene(IGetScene scene);

    void removeViewEventListener(ViewEventListener eventListener);

    void selectActor(IGetActor targetObject);

    void selectEditorTool(EditorTool tool);

    void updateActionList(IGetActor updatedActor);

    void updateActor(IGetActor updatedActor);

    void updateScene(IGetScene currentScene);

    void updateTile(Object updatedObject, IGetTile updatedTile);
}
