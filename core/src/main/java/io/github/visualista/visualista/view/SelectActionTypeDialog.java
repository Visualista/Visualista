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

public class SelectActionTypeDialog extends Dialog {

    private final static String TITLE = "Select action type";
    private Label instructions;
    private TextButton okButton;
    private TextButton cancelButton;
    private List<String> list;
    private final String[] names;
    private final Dialog[] dialogs;

    public SelectActionTypeDialog(Skin skin, String windowStyleName,
            Dialog[] dialogs, String[] names) {
        super(TITLE, skin, windowStyleName);
        this.names = names;
        this.dialogs = dialogs;
        init(skin);
    }

    public SelectActionTypeDialog(Skin skin, IGetActor actor, Dialog[] dialogs,
            String[] names) {
        super(TITLE, skin);
        this.names = names;
        this.dialogs = dialogs;
        init(skin);
    }

    private void init(Skin skin) {
        instructions = new Label("Select action type", skin);
        getContentTable().add(instructions).row();
        list = new List<String>(skin);
        list.setItems(names);
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
            Dialog dialog = dialogs[list.getSelectedIndex()];
            dialog.invalidateHierarchy();
            dialog.invalidate();
            dialog.layout();
            dialog.show(getStage());
        }
        super.result(object);
    }

}
