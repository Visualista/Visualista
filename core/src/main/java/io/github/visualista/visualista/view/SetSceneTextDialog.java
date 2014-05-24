package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.model.IGetActor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SetSceneTextDialog extends Dialog {

    private static final String TITLE = "SetSceneTextAction";
    private Label instructions;
    private TextArea textArea;
    private TextButton okButton;
    private TextButton cancelButton;
    private final ViewEventManager eventManager;
    private final IGetActor actor;

    public SetSceneTextDialog(final Skin skin, final String windowStyleName,
            final IGetActor actor, final ViewEventManager eventManager) {
        super(TITLE, skin, windowStyleName);
        this.actor = actor;
        this.eventManager = eventManager;
        init(skin);
    }

    public SetSceneTextDialog(final Skin skin, final IGetActor actor,
            final ViewEventManager eventManager) {
        super(TITLE, skin);
        this.actor = actor;
        this.eventManager = eventManager;
        init(skin);
    }

    private void init(final Skin skin) {
        instructions = new Label("Input New Scene String", skin);
        getContentTable().add(instructions).row();

        textArea = new TextArea("", skin);
        getContentTable().add(textArea).width(400).height(250);

        okButton = new TextButton("OK", skin);
        button(okButton, true);

        cancelButton = new TextButton("Cancel", skin);
        button(cancelButton, false);
    }

    @Override
    protected void result(final Object object) {
        if ((Boolean) object) {
            eventManager.fireViewEvent(this, Type.ADD_SET_SCENE_TEXT_ACTION,
                    actor, textArea.getText());
        }
        super.result(object);
    }

}
