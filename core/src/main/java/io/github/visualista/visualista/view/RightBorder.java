package io.github.visualista.visualista.view;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.util.Dimension;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class RightBorder extends Border implements Updateable {
    private static final float SCROLL_BORDER_HEIGHT_RATIO = 0.7f;
    private static final int RIGHT_BORDER_LINE_SIZE = 1;
    private static final Color RIGHT_BORDER_COLOR = Color.BLACK;
    private static final float RIGHT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final float RIGHT_BORDER_X_DISPLACEMENT_RATIO = 0.75f;
    private static final float RIGHT_BORDER_HEIGHT_RATIO = 1f;
    private static final float RIGHT_BORDER_WIDTH_RATIO = 0.25f;
    private final EditorView rightBorder;
    private Image actorImage;
    private TextField actorField;
    private TextButton modifyButton;
    private TextButton addActionButton;
    private IGetActor selectedActor;
    private final java.util.List<IGetScene> sceneList = new ArrayList<IGetScene>();
    private java.util.List<IGetActor> actorsInScene;
    protected Dimension gridSize;


    public RightBorder(final EditorView editorView) {
        rightBorder = editorView;
        resizeRightBorder();
        createRightEditorBorderContent();
        resizeRightBorder();

    }

    public void addNewActor(final IGetActor actor) {
        actorsInScene.add(actor);
    }

    public void updateScene(final IGetScene scene) {
        actorsInScene = scene.getActorsInScene();
        gridSize = scene.getIGetGrid().getSize();
    }

    public void changeActiveScene(final IGetScene scene) {
        rightBorder.rightVerticalGroup.setVisible(false);
        actorsInScene = scene.getActorsInScene();
        gridSize = scene.getIGetGrid().getSize();
    }

    public void addNewScene(final IGetScene newScene) {
        sceneList.add(newScene);

    }

    public void updateActor(final IGetActor updatedActor) {
        if (updatedActor == selectedActor) {
            setActorImage(ModelToGdxHelper.createDrawableFor(updatedActor));
            updateActionList(updatedActor);
        }

    }

    public void selectActor(final IGetActor actor) {
        selectedActor = actor;
        if (actor != null) {
            rightBorder.rightVerticalGroup.setVisible(true);
            actorField.setText(actor.getName());
            setActorImage(ModelToGdxHelper.createDrawableFor(actor));
            updateActionList(actor);
        } else {
            rightBorder.rightVerticalGroup.setVisible(false);
        }
    }

    public void setActorImage(final TextureRegionDrawable createDrawableFor) {
        actorImage.setDrawable(createDrawableFor);

    }

    private void resizeRightBorder() {
        setSize(RightBorder.RIGHT_BORDER_WIDTH_RATIO
                * rightBorder.stage.getWidth(),
                RightBorder.RIGHT_BORDER_HEIGHT_RATIO
                * rightBorder.stage.getHeight());
        setPosition(RightBorder.RIGHT_BORDER_X_DISPLACEMENT_RATIO
                * rightBorder.stage.getWidth(),
                RightBorder.RIGHT_BORDER_Y_DISPLACEMENT_RATIO
                * rightBorder.stage.getHeight());
        setLineSize(RightBorder.RIGHT_BORDER_LINE_SIZE);
        setColor(RightBorder.RIGHT_BORDER_COLOR);
    }

    private void createRightEditorBorderContent() {
        rightBorder.rightVerticalGroup = new VerticalGroup();
        setActor(rightBorder.rightVerticalGroup);

        actorField = new TextField("", rightBorder.uiSkin);
        rightBorder.rightVerticalGroup.addActor(actorField);
        rightBorder.rightVerticalGroup.setVisible(false);

        actorField.addCaptureListener(new FocusListener() {

            @Override
            public void keyboardFocusChanged(final FocusEvent event, final Actor actor,
                    final boolean focused) {
                if (!focused) {
                    rightBorder.eventManager.fireViewEvent(this,
                            Type.CHANGE_ACTOR_NAME, selectedActor,
                            actorField.getText());
                }
                super.keyboardFocusChanged(event, actor, focused);
            }

        });
        actorImage = new Image((TextureRegionDrawable) null);
        Border actorImageBorder = new Border();
        actorImageBorder.setSize(70, 70);
        actorImageBorder.setLineSize(1);
        actorImageBorder.setLineOutsideActor(true);
        actorImageBorder.setActor(actorImage);
        rightBorder.rightVerticalGroup.addActor(actorImageBorder);

        actorImage.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                // TODO selected scene and image
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Select image (*.png)", "png");
                rightBorder.filePicker.openFileDialog(new FilePickerListener() {

                    @Override
                    public void fileOpened(final File selectedFile) {
                        rightBorder.eventManager.fireViewEvent(this,
                                Type.CHANGE_ACTOR_IMAGE, selectedActor,
                                selectedFile);
                    }

                    @Override
                    public void fileSaved(final File selectedFile) {
                    }
                }, filter);

                super.clicked(event, x, y);
            }

        });

        rightBorder.actionLabel = new Label("Actions", rightBorder.uiSkin);
        rightBorder.actionLabel.setSize(50, 50);
        rightBorder.actionLabel.setColor(Color.BLACK);
        rightBorder.rightVerticalGroup.addActor(rightBorder.actionLabel);

        rightBorder.actionList = new List<IAction>(rightBorder.uiSkin);

        rightBorder.actionList.setWidth(getWidth() - getLineSize() * 4);
        rightBorder.actionList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(rightBorder.actionList,
                rightBorder.uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setLineOutsideActor(true);
        scrollBorder.setLineSize(1);
        scrollBorder.setSize(rightBorder.actionList.getWidth(), getHeight()
                * SCROLL_BORDER_HEIGHT_RATIO);
        scrollBorder.setActor(scroll);
        rightBorder.rightVerticalGroup.addActor(scrollBorder);

        modifyButton = new TextButton("Modify", rightBorder.uiSkin);
        modifyButton.setSize(150, 20);

        addActionButton = new TextButton("Add", rightBorder.uiSkin);
        addActionButton.setSize(150, 20);
        addActionButton.addCaptureListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Dialog[] dialogs = {
                        new ChangeSceneDialog(rightBorder.uiSkin,
                                selectedActor, sceneList,
                                rightBorder.eventManager),
                                new SetSceneTextDialog(rightBorder.uiSkin,
                                        selectedActor, rightBorder.eventManager),
                                        new AddActorDialog(rightBorder.uiSkin, selectedActor,
                                                actorsInScene, gridSize,
                                                rightBorder.eventManager), };
                String[] dialogTexts = { "Set scene action",
                        "Set scene text action", "Set actor action", };
                Dialog dialog = new SelectActionTypeDialog(rightBorder.uiSkin,
                        selectedActor, dialogs, dialogTexts);
                dialog.invalidateHierarchy();
                dialog.invalidate();
                dialog.layout();
                dialog.show(rightBorder.stage);
                super.clicked(event, x, y);
            }

        });
        HorizontalGroup buttonContainer = new HorizontalGroup();
        buttonContainer.addActor(modifyButton);
        buttonContainer.addActor(addActionButton);

        rightBorder.rightVerticalGroup.addActor(buttonContainer);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    public void updateActionList(final IGetActor updatedActor) {
        IAction[] updatedActionList = new IAction[updatedActor.getActions()
                                                  .size()];
        rightBorder.actionList.setItems(updatedActor.getActions().toArray(
                updatedActionList));
    }
}
