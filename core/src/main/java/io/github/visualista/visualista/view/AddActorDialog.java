package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.PositionedActor;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Point;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
    private java.util.List<IGetActor> actorList;
    private IGetActor actor;
    private Actor placeHolderActor = new Actor();
    private int placeHolderX = 1;
    private int placeHolderY = 1;
    private TextField columnInput;
    private TextField rowInput;
    private Dimension gridSize;

    public AddActorDialog(Skin skin, String windowStyleName, IGetActor actor,
            java.util.List<IGetActor> actorList, Dimension gridSize,
            ViewEventManager eventManager) {
        super(TITLE, skin, windowStyleName);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        this.gridSize = gridSize;
        init(skin);
    }

    public AddActorDialog(Skin skin, IGetActor actor,
            java.util.List<IGetActor> actorList, Dimension gridSize,
            ViewEventManager eventManager) {
        super(TITLE, skin);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        this.gridSize = gridSize;
        init(skin);
    }

    private void init(Skin skin) {
        instructions = new Label("Select an Actor and where to place it", skin);
        getContentTable().add(instructions).row();
        IGetActor[] actorListArray = new IGetActor[actorList.size()];
        list = new List<IGetActor>(skin);
        list.setItems(actorList.toArray(actorListArray));
        ScrollPane scroll = new ScrollPane(list, skin);
        getContentTable().add(scroll).row();
        getContentTable().add(
                new Label("Column (1-" + gridSize.getWidth() + "):", skin));
        columnInput = new TextField("1", skin);
        getContentTable().add(columnInput).row();
        getContentTable().add(
                new Label("Row (1-" + gridSize.getHeight() + "):", skin));
        rowInput = new TextField("1", skin);
        getContentTable().add(rowInput);

        okButton = new TextButton("OK", skin);
        button(okButton, true);

        cancelButton = new TextButton("Cancel", skin);
        button(cancelButton, false);
    }

    @Override
    protected void result(Object object) {
        if ((Boolean) object) {
            eventManager.fireViewEvent(this, Type.ADD_SET_ACTOR_ACTION, actor,
                    new PositionedActor(new Point(placeHolderX, placeHolderY),
                            placeHolderActor));
        }
        super.result(object);
    }

}
