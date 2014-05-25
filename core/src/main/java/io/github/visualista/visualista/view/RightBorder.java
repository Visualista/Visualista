package io.github.visualista.visualista.view;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class RightBorder extends Border implements Updateable {
    private static final float ACTION_LIST_WIDTH_RATIO = 0.9f;
    private static final float ACTOR_IMAGE_SIZE_RATIO = 0.1f;
    private static final Color RIGHT_BORDER_COLOR = Color.BLACK;
    private static final float RIGHT_BORDER_HEIGHT_RATIO = 1f;
    private static final int RIGHT_BORDER_LINE_SIZE = 1;
    private static final float RIGHT_BORDER_WIDTH_RATIO = 0.25f;
    private static final float RIGHT_BORDER_X_DISPLACEMENT_RATIO = 0.75f;
    private static final float RIGHT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final float SCROLL_BORDER_HEIGHT_RATIO = 0.7f;
    private Label actionLabel;
    private List<IAction> actionList;
    private TextField actorField;
    private Image actorImage;
    private Border actorImageBorder;
    private java.util.List<IGetActor> actorsInScene;
    private TextButton addActionButton;
    private final ViewEventManager eventManager;
    private final IFilePicker filePicker;
    protected Dimension gridSize;
    private TextButton modifyButton;
    private final EditorView rightBorder;
    private VerticalGroup rightVerticalGroup;
    private final java.util.List<IGetScene> sceneList = new ArrayList<IGetScene>();
    private IGetActor selectedActor;
    private final Skin uiSkin;

    public RightBorder(final EditorView editorView, final ViewEventManager eventManager, final IFilePicker filePicker, final Skin uiSkin) {
        this.eventManager = eventManager;
        this.filePicker = filePicker;
        this.uiSkin = uiSkin;
        rightBorder = editorView;
        resize();
        createRightEditorBorderContent();
        resize();

    }

    public void addNewActor(final IGetActor actor) {
        actorsInScene.add(actor);
    }

    public void addNewScene(final IGetScene newScene) {
        sceneList.add(newScene);

    }

    public void changeActiveScene(final IGetScene scene) {
        rightVerticalGroup.setVisible(false);
        actorsInScene = scene.getActorsInScene();
        gridSize = scene.getIGetGrid().getSize();
    }

    private void createRightEditorBorderContent() {
        rightVerticalGroup = new VerticalGroup();
        setActor(rightVerticalGroup);

        actorField = new TextField("", uiSkin);
        rightVerticalGroup.addActor(actorField);
        rightVerticalGroup.setVisible(false);

        actorField.addCaptureListener(new FocusListener() {

            @Override
            public void keyboardFocusChanged(final FocusEvent event,
                    final Actor actor, final boolean focused) {
                if (!focused) {
                    eventManager.fireViewEvent(this,
                            Type.CHANGE_ACTOR_NAME, selectedActor,
                            actorField.getText());
                }
                super.keyboardFocusChanged(event, actor, focused);
            }

        });
        actorImage = new Image((TextureRegionDrawable) null);
        actorImageBorder = new Border();

        actorImageBorder.setLineSize(1);
        actorImageBorder.setLineOutsideActor(true);
        actorImageBorder.setActor(actorImage);
        rightVerticalGroup.addActor(actorImageBorder);

        actorImage.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                // TODO selected scene and image
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Select image (*.png)", "png");
                filePicker.openFileDialog(new FilePickerListener() {

                    @Override
                    public void fileOpened(final File selectedFile) {
                        eventManager.fireViewEvent(this,
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

        actionLabel = new Label("Actions", uiSkin);
        actionLabel.setColor(Color.BLACK);
        rightVerticalGroup.addActor(actionLabel);

        actionList = new List<IAction>(uiSkin);

        actionList.setWidth(getWidth() * ACTION_LIST_WIDTH_RATIO);
        actionList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actionList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setLineOutsideActor(true);
        scrollBorder.setLineSize(1);
        scrollBorder.setSize(actionList.getWidth(), getHeight()
                * SCROLL_BORDER_HEIGHT_RATIO);
        scrollBorder.setActor(scroll);
        rightVerticalGroup.addActor(scrollBorder);

        modifyButton = new TextButton("Modify", uiSkin);

        addActionButton = new TextButton("Add", uiSkin);
        addActionButton.addCaptureListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                Dialog[] dialogs = {
                        new ChangeSceneDialog(uiSkin,
                                selectedActor, sceneList,
                                eventManager),
                                new SetSceneTextDialog(uiSkin,
                                        selectedActor, eventManager),
                                        new AddActorDialog(uiSkin, selectedActor,
                                                actorsInScene, gridSize,
                                                eventManager), };
                String[] dialogTexts = { "Set scene action",
                        "Set scene text action", "Set actor action", };
                Dialog dialog = new SelectActionTypeDialog(uiSkin,
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

        rightVerticalGroup.addActor(buttonContainer);
    }

    public void resize() {
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
        if (actorImageBorder != null) {
            actorImageBorder.setSize(rightBorder.stage.getWidth()
                    * ACTOR_IMAGE_SIZE_RATIO, rightBorder.stage.getWidth()
                    * ACTOR_IMAGE_SIZE_RATIO);
        }
    }

    public void selectActor(final IGetActor actor) {
        selectedActor = actor;
        if (actor != null) {
            rightVerticalGroup.setVisible(true);
            actorField.setText(actor.getName());
            setActorImage(ModelToGdxHelper.createDrawableFor(actor));
            updateActionList(actor);
        } else {
            rightVerticalGroup.setVisible(false);
        }
    }

    public void setActorImage(final TextureRegionDrawable createDrawableFor) {
        actorImage.setDrawable(createDrawableFor);

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    public void updateActionList(final IGetActor updatedActor) {
        IAction[] updatedActionList = new IAction[updatedActor.getActions()
                                                  .size()];
        actionList.setItems(updatedActor.getActions()
                .toArray(updatedActionList));
    }

    public void updateActor(final IGetActor updatedActor) {
        if (updatedActor == selectedActor) {
            setActorImage(ModelToGdxHelper.createDrawableFor(updatedActor));
            updateActionList(updatedActor);
        }

    }

    public void updateScene(final IGetScene scene) {
        actorsInScene = scene.getActorsInScene();
        gridSize = scene.getIGetGrid().getSize();
    }
}
