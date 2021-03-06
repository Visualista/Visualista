package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.PositionedActor;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.Point;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;

public class AddActorDialog extends Dialog {

    private static final int DEFAULT_INPUT_INT = 1;
    private static final String TEXT_INPUT_DEFAULT_VALUE = "1";
    private static final String TITLE = "AddActorAction";
    private Label instructions;
    private TextButton okButton;
    private TextButton cancelButton;
    private final ViewEventListener eventManager;
    private List<IGetActor> list;
    private final java.util.List<IGetActor> actorList;
    private final IGetActor actor;
    private TextField columnInput;
    private TextField rowInput;
    private final Dimension gridSize;

    public AddActorDialog(final Skin skin, final String windowStyleName,
            final IGetActor actor, final java.util.List<IGetActor> actorList,
            final Dimension gridSize, final ViewEventListener eventManager) {
        super(TITLE, skin, windowStyleName);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        this.gridSize = gridSize;
        init(skin);
    }

    public AddActorDialog(final Skin skin, final IGetActor actor,
            final java.util.List<IGetActor> actorList,
            final Dimension gridSize, final ViewEventListener eventManager) {
        super(TITLE, skin);
        this.actor = actor;
        this.actorList = actorList;
        this.eventManager = eventManager;
        this.gridSize = gridSize;
        init(skin);
    }

    private void init(final Skin skin) {
        instructions = new Label("Select an Actor and where to place it", skin);
        getContentTable().add(instructions).row();
        IGetActor[] actorListArray = new IGetActor[actorList.size()];
        list = new List<IGetActor>(skin);
        list.setItems(actorList.toArray(actorListArray));
        ScrollPane scroll = new ScrollPane(list, skin);
        getContentTable().add(scroll).row();
        getContentTable().add(
                new Label(labelFormat("Column", gridSize.getWidth()), skin));
        columnInput = new TextField(TEXT_INPUT_DEFAULT_VALUE, skin);
        columnInput
        .setTextFieldFilter(createInputFilter(1, gridSize.getWidth()));
        getContentTable().add(columnInput).row();
        getContentTable().add(
                new Label(labelFormat("Row", gridSize.getHeight()), skin));
        rowInput = new TextField(TEXT_INPUT_DEFAULT_VALUE, skin);
        rowInput.setTextFieldFilter(createInputFilter(1, gridSize.getHeight()));
        getContentTable().add(rowInput);
        okButton = new TextButton("OK", skin);
        button(okButton, true);

        cancelButton = new TextButton("Cancel", skin);
        button(cancelButton, false);
    }

    private String labelFormat(final String prefix, final int max) {
        return prefix + " (1-" + max + "):";
    }

    private TextFieldFilter createInputFilter(final int min, final int max) {
        return new TextFieldFilter() {

            @Override
            public boolean acceptChar(final TextField textField, final char c) {
                return Character.isDigit(c)
                        && Integer.parseInt(textField.getText() + c) >= min
                        && Integer.parseInt(textField.getText() + c) <= max;
            }

        };
    }

    @Override
    protected void result(final Object object) {
        if ((Boolean) object) {
            String columnText = columnInput.getText();
            int column = getIntFrom(columnText, DEFAULT_INPUT_INT);
            String rowText = rowInput.getText();
            int row = getIntFrom(rowText, DEFAULT_INPUT_INT);
            eventManager.addSetActorAction(actor, new PositionedActor(
                    new Point(column, row), (Actor) list.getSelected()));
        }
        super.result(object);
    }

    private int getIntFrom(final String columnText, final int defaultNumber) {
        if (columnText.isEmpty()) {
            return 1;
        } else {
            return Integer.parseInt(columnText);
        }

    }

}
