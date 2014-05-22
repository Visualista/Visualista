package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetActor;
import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AddActorDialog extends Dialog {

    private final static String TITLE = "AddActorAction";
    private Label instructions;
    private TextButton okButton;
    private TextButton cancelButton;
    private ViewEventManager eventManager;
    private List<IGetActor> list;
    private ArrayList<IGetActor> actorList;
    private IGetActor actor;

    public AddActorDialog(Skin skin, String windowStyleName,
            IGetActor actor, ArrayList<IGetActor> actorList,
            ViewEventManager eventManager) {
        super(TITLE, skin, windowStyleName);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        init(skin);
    }

    public AddActorDialog(Skin skin, IGetActor actor,
            ArrayList<IGetActor> actorList, ViewEventManager eventManager) {
        super(TITLE, skin);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        init(skin);
    }

    private void init(Skin skin) {
        instructions = new Label("Select an Actor and where to place it", skin);
        getContentTable().add(instructions).row();

        IGetActor[] actorListArray = new IGetActor[actorList.size()];
        list = new List<IGetActor>(skin);
        list.setItems(actorList.toArray(actorListArray));
        ScrollPane scroll = new ScrollPane(list, skin);
        getContentTable().add(scroll);
        
        //TODO ngt sätt att välja tile

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
