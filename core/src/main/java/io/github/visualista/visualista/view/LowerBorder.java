package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.model.IGetScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

class LowerBorder extends Border implements Updateable {

    private static final int LOWER_BORDER_LINE_SIZE = 1;
    private static final Color LOWER_BORDER_COLOR = Color.BLACK;
    private static final float LOWER_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final float LOWER_BORDER_X_DISPLACEMENT_RATIO = 0.25f;
    private static final float LOWER_BORDER_WIDTH_RATIO = 0.5f;
    private static final float LOWER_BORDER_HEIGHT_RATIO = 0.2f;
    private final EditorView lowerBorder;
    private final ViewEventManager eventManeger;
    private TextArea textArea;


    public LowerBorder(final EditorView editorView, final Stage stage, final ViewEventManager eventManeger) {
        lowerBorder = editorView;
        resizeLowerBorder();
        setActor(createLowerBorderContent());
        resizeLowerBorder();
        this.eventManeger = eventManeger;
        stage.addActor(this);
    }

    public void changeActiveScene(final IGetScene scene) {
        textArea.setText(scene.getStoryText());
    }

    private void resizeLowerBorder() {
        setSize(LowerBorder.LOWER_BORDER_WIDTH_RATIO * lowerBorder.stage.getWidth(),
                LowerBorder.LOWER_BORDER_HEIGHT_RATIO * lowerBorder.stage.getHeight());
        setPosition(LowerBorder.LOWER_BORDER_X_DISPLACEMENT_RATIO * lowerBorder.stage.getWidth(),
                LowerBorder.LOWER_BORDER_Y_DISPLACEMENT_RATIO * lowerBorder.stage.getHeight());
        setLineSize(LowerBorder.LOWER_BORDER_LINE_SIZE);
        setColor(LowerBorder.LOWER_BORDER_COLOR);

    }

    private TextArea createLowerBorderContent() {
        textArea = new TextArea("", lowerBorder.uiSkin);

        textArea.addCaptureListener(new FocusListener() {

            @Override
            public void keyboardFocusChanged(final FocusEvent event, final Actor actor,
                    final boolean focused) {
                if (!focused) {
                    lowerBorder.eventManager.fireViewEvent(this,
                            Type.CHANGE_SCENE_TEXT, lowerBorder.activeScene,
                            textArea.getText());
                }
                super.keyboardFocusChanged(event, actor, focused);
            }

        });
        return textArea;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
}