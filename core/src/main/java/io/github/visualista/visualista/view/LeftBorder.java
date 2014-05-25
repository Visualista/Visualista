package io.github.visualista.visualista.view;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

class LeftBorder extends Border implements Updateable {

    private static final int BUTTON_DIMENSION = 40;
    private static final int BUTTON_EXTRA_WIDTH = 10;
    static final Color LEFT_BORDER_COLOR = Color.BLACK;
    static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    static final int LEFT_BORDER_LINE_SIZE = 1;
    static final float LEFT_BORDER_WIDTH_RATIO = 0.25f;
    static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final float LIST_WIDTH_RATIO = 0.9f;
    private static final float SCROLL_BORDER_HEIGHT_RATIO = 0.7f;
    protected IGetScene activeScene;

    private List<IGetActor> actorList;

    private VerticalGroup leftVerticalGroup;
    private final EditorView leftView;

    private TextButton setSceneBackgroundButton;


    public LeftBorder(final EditorView editorView) {
        leftView = editorView;
        resizeLeftBorder();
        createLeftBorderContent();
    }

    public void addNewActor(final IGetActor updatedActor) {
        actorList.getItems().add(updatedActor);
        actorList.setSelectedIndex(leftView.leftBorder.actorList.getItems()
                .indexOf(updatedActor, true));

    }

    public void changeActiveScene(final IGetScene scene) {
        activeScene = scene;
        IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
        actorList.setItems(new Array<IGetActor>(scene.getActorsInScene()
                .toArray(temp)));
        if (actorList.getSelected() != null) {
            leftView.eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                    actorList.getSelected());
        }
    }

    private List<IGetActor> createActorList() {
        final List<IGetActor> list = new List<IGetActor>(leftView.uiSkin);

        list.setWidth(getWidth() * LIST_WIDTH_RATIO);
        list.setColor(Color.BLACK);
        list.addListener(new ChangeListener() {

            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    leftView.eventManager.fireViewEvent(this,
                            Type.SELECT_ACTOR, list.getSelected());
                }
            }
        });
        return list;
    }

    private TextButton createAddActorButton() {
        TextButton addActorButton = new TextButton("Add actor", leftView.uiSkin);

        addActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                leftView.eventManager.fireViewEvent(this,
                        Type.NEW_ACTOR, activeScene);
            }
        });
        return addActorButton;
    }

    private Actor createArrowButton() {
        final Drawable arrow = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/arrow.png"))));

        ImageButton arrowButton = new ImageButton(arrow);
        final Border border = leftView.surroundWithInvisibleBorder(arrowButton);
        border.setLineOutsideActor(true);
        border.setLineSize(1);
        leftView.selectedTool = EditorTool.ARROW;
        arrowButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                leftView.selectedTool = EditorTool.ARROW;
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        leftView.toolButtonBorders.add(border);
        return border;
    }

    private Actor createButtonContainer1(final Actor saveButtonBorder,
            final Actor openButtonBorder, final Actor cursorButtonBorder,
            final Actor arrowButtonBorder) {
        HorizontalGroup buttonContainer1 = new HorizontalGroup();
        buttonContainer1.addActor(saveButtonBorder);
        buttonContainer1.addActor(openButtonBorder);
        buttonContainer1.addActor(cursorButtonBorder);
        buttonContainer1.addActor(arrowButtonBorder);
        return buttonContainer1;
    }

    private Actor createButtonGroup1Buttons() {
        Actor openButton = createOpenButton();
        Actor saveButton = createSaveButton();
        Actor cursorButton = createCursorButton();
        Actor arrowButton = createArrowButton();
        resizeButtonGroup1Buttons(openButton, saveButton, cursorButton,
                arrowButton);
        return createButtonContainer1(openButton, saveButton, cursorButton,
                arrowButton);
    }

    private Actor createCursorButton() {
        final Drawable cursor = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        ImageButton cursorButton = new ImageButton(cursor);
        final Border border = leftView
                .surroundWithInvisibleBorder(cursorButton);
        border.setLineOutsideActor(true);
        cursorButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                leftView.selectedTool = EditorTool.CURSOR;
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        leftView.toolButtonBorders.add(border);
        return border;
    }



    private void createLeftBorderContent() {
        createLeftVerticalGroup();
        Actor buttonGroup1 = createButtonGroup1Buttons();
        leftVerticalGroup.addActor(buttonGroup1);
        actorList = createActorList();
        final ScrollPane scroll = new ScrollPane(actorList, leftView.uiSkin);
        scroll.setFadeScrollBars(false);
        createScrollBorder(scroll);
        TextButton addActorButton = createAddActorButton();
        TextButton removeActorButton = createRemoveActorButton();

        HorizontalGroup buttonContainer2 = new HorizontalGroup();

        LibGdxConvenience.addActorsTo(buttonContainer2, addActorButton,
                removeActorButton);

        leftVerticalGroup.addActor(buttonContainer2);

        createSetSceneBackgroundButton();
    }

    // End create Editor //

    private void createLeftVerticalGroup() {
        leftVerticalGroup = new VerticalGroup();
        setActor(leftVerticalGroup);
    }

    private Actor createOpenButton() {
        final Drawable open = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/open.png"))));
        ImageButton openButton = new ImageButton(open);
        openButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                leftView.openNovel();
                super.clicked(event, x, y);
            }

        });
        return leftView.surroundWithInvisibleBorder(openButton);
    }

    private TextButton createRemoveActorButton() {
        TextButton removeActorButton = new TextButton("Remove actor",
                leftView.uiSkin);

        removeActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                // TODO list linking
                leftView.eventManager
                .fireViewEvent(this, Type.REMOVE_ACTOR,
                        activeScene);
            }
        });
        return removeActorButton;
    }

    private Actor createSaveButton() {
        final Drawable save = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/save.png"))));
        ImageButton saveButton = new ImageButton(save);
        saveButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                leftView.saveNovel();
                super.clicked(event, x, y);
            }

        });
        return leftView.surroundWithInvisibleBorder(saveButton);
    }

    private void createScrollBorder(final ScrollPane scroll) {
        Border scrollBorder = new Border();
        scrollBorder.setSize(actorList.getWidth(), getHeight()
                * SCROLL_BORDER_HEIGHT_RATIO);
        scrollBorder.setActor(scroll);
        leftVerticalGroup.addActor(scrollBorder);
    }

    private void createSetSceneBackgroundButton() {
        setSceneBackgroundButton = new TextButton("Set background",
                leftView.uiSkin);
        leftVerticalGroup.addActor(setSceneBackgroundButton);

        setSceneBackgroundButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                // TODO selected scene and image
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Chose image", "png");
                leftView.filePicker.openFileDialog(
                        new FilePickerListener() {

                            @Override
                            public void fileOpened(final File selectedFile) {
                                if (selectedFile != null) {
                                    leftView.eventManager
                                    .fireViewEvent(
                                            this,
                                            Type.CHANGE_SCENE_IMAGE,
                                            activeScene,
                                            selectedFile);
                                }
                            }

                            @Override
                            public void fileSaved(final File selectedFile) {
                            }
                        }, filter);

                super.clicked(event, x, y);
            }

        });
    }

    protected void hideButtonBorders() {
        for (Border border : leftView.toolButtonBorders) {
            border.setLineSize(0);
        }
    }

    private void resizeButtonGroup1Buttons(final Actor saveButtonBorder,
            final Actor openButtonBorder, final Actor cursorButtonBorder,
            final Actor arrowButtonBorder) {
        openButtonBorder.setSize(BUTTON_DIMENSION+BUTTON_EXTRA_WIDTH, BUTTON_DIMENSION);
        saveButtonBorder.setSize(BUTTON_DIMENSION+BUTTON_EXTRA_WIDTH, BUTTON_DIMENSION);
        cursorButtonBorder.setSize(BUTTON_DIMENSION+BUTTON_EXTRA_WIDTH, BUTTON_DIMENSION);
        arrowButtonBorder.setSize(BUTTON_DIMENSION+BUTTON_EXTRA_WIDTH, BUTTON_DIMENSION);
    }

    private void resizeLeftBorder() {
        setSize(LeftBorder.LEFT_BORDER_WIDTH_RATIO * leftView.stage.getWidth(),
                LeftBorder.LEFT_BORDER_HEIGHT_RATIO
                * leftView.stage.getHeight());
        setPosition(
                LeftBorder.LEFT_BORDER_X_DISPLACEMENT_RATIO
                * leftView.stage.getWidth(),
                LeftBorder.LEFT_BORDER_Y_DISPLACEMENT_RATIO
                * leftView.stage.getHeight());
        setLineSize(LeftBorder.LEFT_BORDER_LINE_SIZE);
        setColor(LeftBorder.LEFT_BORDER_COLOR);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
