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

    void ADD_SET_ACTOR_ACTION(IGetActor actor, PositionedActor positionedActor);

    void SELECT_TILE(IGetTile tile);

    void ADD_SET_SCENE_ACTION(IGetActor actor, IGetScene selected);

    void SELECT_ACTOR(IGetActor selected);

    void NEW_ACTOR(IGetScene activeScene);

    void SELECT_EDITOR_TOOl(EditorTool arrow);

    void REMOVE_ACTOR(IGetScene activeScene);

    void FILE_OPEN(File selectedFile);

    void FILE_SAVE(File selectedFile);

    void CHANGE_SCENE_IMAGE(IGetScene activeScene, File selectedFile);

    void CHANGE_SCENE_TEXT(IGetScene activeScene, String text);

    void CHANGE_ACTOR_NAME(IGetActor selectedActor, String text);

    void ADD_SET_SCENE_TEXT_ACTION(IGetActor actor, String text);

    void SELECT_SCENE(IGetScene value);

    void CHANGE_SCENE_NAME(IGetScene value, String newName);

    void VIEW_READY();

    void NEW_SCENE();

    void CHANGE_ACTOR_IMAGE(IGetActor selectedActor, File selectedFile);

    void TILE_SET_ACTOR(com.badlogic.gdx.scenes.scene2d.ui.Image image, IGetTile tile, IGetActor selectedActor);




}

