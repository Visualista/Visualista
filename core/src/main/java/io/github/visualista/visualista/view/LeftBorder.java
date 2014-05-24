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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

class LeftBorder extends Border implements Updateable {

    /**
     * 
     */
    private final EditorView leftView;
    private TextButton addActorButton;
    private TextButton removeActorButton;

    private TextButton setSceneBackgroundButton;

    private VerticalGroup leftVerticalGroup;
    private List<IGetActor> actorList;
    static final int LEFT_BORDER_LINE_SIZE = 1;
    static final Color LEFT_BORDER_COLOR = Color.BLACK;
    static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    static final float LEFT_BORDER_WIDTH_RATIO = 2.5f / 10;

    public LeftBorder(EditorView editorView) {
        leftView = editorView;
        resizeLeftBorder();
        createLeftBorderContent();
    }

    public void changeActiveScene(IGetScene scene) {
        IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
        actorList.setItems(new Array<IGetActor>(scene.getActorsInScene()
                .toArray(temp)));
        if (actorList.getSelected() != null) {
            leftView.eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                    actorList.getSelected());
        }
    }

    public void addNewActor(IGetActor updatedActor) {
        actorList.getItems().add(updatedActor);
        actorList.setSelectedIndex((leftView.leftBorder.actorList.getItems()
                .indexOf(updatedActor, true)));

    }

    private void resizeLeftBorder() {
        setSize(LeftBorder.LEFT_BORDER_WIDTH_RATIO * leftView.stage.getWidth(),
                LeftBorder.LEFT_BORDER_HEIGHT_RATIO * leftView.stage.getHeight());
        setPosition(LeftBorder.LEFT_BORDER_X_DISPLACEMENT_RATIO * leftView.stage.getWidth(),
                LeftBorder.LEFT_BORDER_Y_DISPLACEMENT_RATIO * leftView.stage.getHeight());
        setLineSize(LeftBorder.LEFT_BORDER_LINE_SIZE);
        setColor(LeftBorder.LEFT_BORDER_COLOR);
    }

    private List<IGetActor> createActorList() {
        final List<IGetActor> list = new List<IGetActor>(leftView.uiSkin);

        list.setWidth(getWidth() - getLineSize() * 4);
        list.setColor(Color.BLACK);
        list.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    LeftBorder.this.leftView.eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                            list.getSelected());
                }
            }
        });
        return list;
    }

    private void createLeftVerticalGroup() {
        leftVerticalGroup = new VerticalGroup();
        setActor(leftVerticalGroup);
    }

    private Actor createButtonContainer1(Actor saveButtonBorder,
            Actor openButtonBorder, Actor cursorButtonBorder,
            Actor arrowButtonBorder) {
        HorizontalGroup buttonContainer1 = new HorizontalGroup();
        buttonContainer1.addActor(saveButtonBorder);
        buttonContainer1.addActor(openButtonBorder);
        buttonContainer1.addActor(cursorButtonBorder);
        buttonContainer1.addActor(arrowButtonBorder);
        return buttonContainer1;
    }

    private void createScrollBorder(final ScrollPane scroll) {
        Border scrollBorder = new Border();
        scrollBorder.setSize(actorList.getWidth(), getHeight() * 0.7f);
        scrollBorder.setActor(scroll);
        leftVerticalGroup.addActor(scrollBorder);
    }

    private void createLeftBorderContent() {
        createLeftVerticalGroup();
        Actor buttonGroup1 = createButtonGroup1Buttons();
        leftVerticalGroup.addActor(buttonGroup1);
        actorList = createActorList();
        final ScrollPane scroll = new ScrollPane(actorList, leftView.uiSkin);
        scroll.setFadeScrollBars(false);
        createScrollBorder(scroll);
        createAddActorButton();
        createRemoveActorButton();

        HorizontalGroup buttonContainer2 = new HorizontalGroup();

        buttonContainer2.addActor(addActorButton);

        leftVerticalGroup.addActor(buttonContainer2);

        createSetSceneBackgroundButton();
    }

    private void createRemoveActorButton() {
        removeActorButton = new TextButton("Remove actor", leftView.uiSkin);
        removeActorButton.setSize(150, 20);

        removeActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO list linking
                LeftBorder.this.leftView.eventManager.fireViewEvent(this, Type.REMOVE_ACTOR,
                        LeftBorder.this.leftView.activeScene);
            }
        });
    }

    // End create Editor //

    private void createAddActorButton() {
        addActorButton = new TextButton("Add actor", leftView.uiSkin);
        addActorButton.setSize(150, 20);

        addActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LeftBorder.this.leftView.eventManager.fireViewEvent(this, Type.NEW_ACTOR,
                        LeftBorder.this.leftView.activeScene);
            }
        });
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

    private void resizeButtonGroup1Buttons(Actor saveButtonBorder,
            Actor openButtonBorder, Actor cursorButtonBorder,
            Actor arrowButtonBorder) {
        openButtonBorder.setSize(50, 40);
        saveButtonBorder.setSize(50, 40);
        cursorButtonBorder.setSize(50, 40);
        arrowButtonBorder.setSize(50, 40);
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
            public void clicked(InputEvent event, float x, float y) {
                LeftBorder.this.leftView.selectedTool = EditorTool.ARROW;
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        leftView.toolButtonBorders.add(border);
        return border;
    }

    protected void hideButtonBorders() {
        for (Border border : leftView.toolButtonBorders) {
            border.setLineSize(0);
        }
    }

    private Actor createCursorButton() {
        final Drawable cursor = new TextureRegionDrawable(
                new TextureRegion(new Texture(
                        Gdx.files.internal("icons/cursor.png"))));
        ImageButton cursorButton = new ImageButton(cursor);
        final Border border = leftView.surroundWithInvisibleBorder(cursorButton);
        border.setLineOutsideActor(true);
        cursorButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                LeftBorder.this.leftView.selectedTool = EditorTool.CURSOR;
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        leftView.toolButtonBorders.add(border);
        return border;
    }

    private Actor createSaveButton() {
        final Drawable save = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/save.png"))));
        ImageButton saveButton = new ImageButton(save);
        saveButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                LeftBorder.this.leftView.saveNovel();
                super.clicked(event, x, y);
            }

        });
        return leftView.surroundWithInvisibleBorder(saveButton);
    }

    private Actor createOpenButton() {
        final Drawable open = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/open.png"))));
        ImageButton openButton = new ImageButton(open);
        openButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                LeftBorder.this.leftView.openNovel();
                super.clicked(event, x, y);
            }

        });
        return leftView.surroundWithInvisibleBorder(openButton);
    }

    private void createSetSceneBackgroundButton() {
        setSceneBackgroundButton = new TextButton("Set background", leftView.uiSkin);
        leftVerticalGroup.addActor(setSceneBackgroundButton);

        setSceneBackgroundButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO selected scene and image
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Chose image", "png");
                LeftBorder.this.leftView.filePicker.openFileDialog(new FilePickerListener() {

                    @Override
                    public void fileOpened(File selectedFile) {
                        if (selectedFile != null) {
                            LeftBorder.this.leftView.eventManager.fireViewEvent(this,
                                    Type.CHANGE_SCENE_IMAGE, LeftBorder.this.leftView.activeScene,
                                    selectedFile);
                        }
                    }

                    @Override
                    public void fileSaved(File selectedFile) {
                    }
                }, filter);

                super.clicked(event, x, y);
            }

        });
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}