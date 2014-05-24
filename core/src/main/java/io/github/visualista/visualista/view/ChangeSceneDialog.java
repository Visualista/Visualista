package io.github.visualista.visualista.view;

import java.util.ArrayList;

import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ChangeSceneDialog extends Dialog {

    private static final String TITLE = "ChangeSceneAction";
    private Label instructions;
    private TextButton okButton;
    private TextButton cancelButton;
    private ViewEventManager eventManager;
    private List<IGetScene> list;
    private ArrayList<IGetScene> sceneList;
    private IGetActor actor;

    public ChangeSceneDialog(Skin skin, String windowStyleName,
            IGetActor actor, ArrayList<IGetScene> sceneList,
            ViewEventManager eventManager) {
        super(TITLE, skin, windowStyleName);
        this.actor = actor;
        this.sceneList = sceneList;
        this.eventManager = eventManager;
        init(skin);
    }

    public ChangeSceneDialog(Skin skin, IGetActor actor,
            ArrayList<IGetScene> sceneList, ViewEventManager eventManager) {
        super(TITLE, skin);
        this.actor = actor;
        this.sceneList = sceneList;
        this.eventManager = eventManager;
        init(skin);
    }

    private void init(Skin skin) {
        instructions = new Label("Select the next scene", skin);
        getContentTable().add(instructions).row();

        IGetScene[] sceneListArray = new IGetScene[sceneList.size()];
        list = new List<IGetScene>(skin);
        list.setItems(sceneList.toArray(sceneListArray));
        ScrollPane scroll = new ScrollPane(list, skin);
        getContentTable().add(scroll);

        okButton = new TextButton("OK", skin);
        button(okButton, true);

        cancelButton = new TextButton("Cancel", skin);
        button(cancelButton, false);
    }

    @Override
    protected void result(Object object) {
        if ((Boolean) object) {
            eventManager.fireViewEvent(this, Type.ADD_SET_SCENE_ACTION, actor,
                    list.getSelected());
        }
        super.result(object);
    }

}
