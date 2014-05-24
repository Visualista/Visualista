package io.github.visualista.visualista.editorcontroller;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;

/** Interface responsible of communicating relevant methods to the EditorController.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */

public interface IEditorView {
    void addScene(IGetScene newScene);

    void removeViewEventListener(ViewEventListener eventListener);

    void addViewEventListener(ViewEventListener eventListener);

    void updateScene(IGetScene currentScene);

    void removeScene(IGetScene scene);

    void changeActiveScene(IGetScene scene);

    void changeActiveNovel(IGetNovel updatedNovel);

    boolean getIsReady();

    void clearModel();

    void selectActor(IGetActor targetObject);

    void updateActor(IGetActor updatedActor);

    void updateTile(Object updatedObject, IGetTile updatedTile);

    void addNewActor(IGetActor updatedActor);

    void updateActionList(IGetActor updatedActor);
}
