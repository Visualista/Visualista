package io.github.visualista.visualista.view;

import java.util.ArrayList;

import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ChangeSceneDialog extends Dialog {

    private final static String TITLE = "ChangeSceneAction";
    private Label instructions;
    private TextArea textArea;
    private TextButton okButton;
    private TextButton cancelButton;
    private ViewEventManager eventManager;
    private List<IGetScene> list;
    private ArrayList<IGetScene> sceneList;

    public ChangeSceneDialog(Skin skin, String windowStyleName, ArrayList<IGetScene> sceneList, ViewEventManager eventManager) {
        super(TITLE, skin, windowStyleName);
        this.sceneList = sceneList;
        this.eventManager = eventManager;
        init(skin);
    }

    public ChangeSceneDialog(Skin skin, ArrayList<IGetScene> sceneList, ViewEventManager eventManager) {
        super(TITLE, skin);
        this.sceneList = sceneList;
        this.eventManager = eventManager;
        init(skin);
    }

    private void init(Skin skin){
        instructions = new Label("Select the next scene", skin);
        getContentTable().add(instructions).row();

        IGetScene[] sceneListArray = new IGetScene[sceneList.size()]; 
        list = new List<IGetScene>(skin);
        list.setItems(sceneList.toArray(sceneListArray));
        //TODO add scrolling
        getContentTable().add(list);

        okButton = new TextButton("OK", skin);
        button(okButton, true);

        cancelButton = new TextButton("Cancel", skin);
        button(cancelButton, false);
    }

    @Override
    protected void result(Object object) {
        if((Boolean)object){
            eventManager.fireViewEvent(this, Type.ADD_SET_SCENE_TEXT_ACTION, actor, textArea.getText());
        }
        super.result(object);
    }

}
