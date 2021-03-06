package io.github.visualista.visualista.view;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.EditorTool;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private static final Color LEFT_BORDER_COLOR = Color.BLACK;
    private static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    private static final int LEFT_BORDER_LINE_SIZE = 1;
    private static final float LEFT_BORDER_WIDTH_RATIO = 0.25f;
    private static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    private static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final float LIST_WIDTH_RATIO = 0.9f;
    private static final float SCROLL_BORDER_HEIGHT_RATIO = 0.7f;
    private static final String VISUALISTA_FILE_DESCRIPTION = "Visualista Novel";
    private static final String VISUALISTA_FILE_FORMAT_WITHOUT_DOT = "vis";
    private IGetScene activeScene;
    private List<IGetActor> actorList;

    private final ViewEventListener eventManager;

    private final IFilePicker filePicker;
    private VerticalGroup leftVerticalGroup;

    private TextButton setSceneBackgroundButton;
    private final Stage stage;
    private final java.util.List<Border> toolButtonBorders;
    private final Skin uiSkin;

    public LeftBorder(final Skin uiSkin, final ViewEventListener eventManager,
            final IFilePicker filePicker, final Stage stage) {
        toolButtonBorders = new ArrayList<Border>();
        this.eventManager = eventManager;
        this.filePicker = filePicker;
        this.uiSkin = uiSkin;
        this.stage = stage;
        resize();
        createLeftBorderContent();
    }

    public void addNewActor(final IGetActor updatedActor) {
        actorList.getItems().add(updatedActor);
        actorList.setSelectedIndex(actorList.getItems().indexOf(updatedActor,
                true));

    }

    public void changeActiveScene(final IGetScene scene) {
        activeScene = scene;
        IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
        actorList.setItems(new Array<IGetActor>(scene.getActorsInScene()
                .toArray(temp)));
        if (actorList.getSelected() != null) {
            eventManager.selectActor(
                    actorList.getSelected());
        }
    }

    private List<IGetActor> createActorList() {
        final List<IGetActor> list = new List<IGetActor>(uiSkin);

        list.setWidth(getWidth() * LIST_WIDTH_RATIO);
        list.setColor(Color.BLACK);
        list.addListener(new ChangeListener() {

            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    eventManager.selectActor(
                            list.getSelected());
                }
            }
        });
        return list;
    }

    private TextButton createAddActorButton() {
        TextButton addActorButton = new TextButton("Add actor", uiSkin);

        addActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                eventManager.newActor(activeScene);
            }
        });
        return addActorButton;
    }

    private Actor createArrowButton() {
        final Drawable arrow = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/arrow.png"))));

        ImageButton arrowButton = new ImageButton(arrow);
        final Border border = LibGdxConvenience
                .surroundWithInvisibleBorder(arrowButton);
        border.setLineOutsideActor(true);
        border.setLineSize(1);
        arrowButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                eventManager.selectEditorTool(
                        EditorTool.ARROW);
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        toolButtonBorders.add(border);
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
        final Border border = LibGdxConvenience
                .surroundWithInvisibleBorder(cursorButton);
        border.setLineOutsideActor(true);
        cursorButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {

                eventManager.selectEditorTool(
                        EditorTool.CURSOR);
                hideButtonBorders();
                border.setLineSize(1);
            }

        });
        toolButtonBorders.add(border);
        return border;
    }

    private void createLeftBorderContent() {
        createLeftVerticalGroup();
        Actor buttonGroup1 = createButtonGroup1Buttons();
        leftVerticalGroup.addActor(buttonGroup1);
        actorList = createActorList();
        final ScrollPane scroll = new ScrollPane(actorList, uiSkin);
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
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                openNovel();
                super.clicked(event, x, y);
            }

        });
        return LibGdxConvenience.surroundWithInvisibleBorder(openButton);
    }

    private TextButton createRemoveActorButton() {
        TextButton removeActorButton = new TextButton("Remove actor", uiSkin);

        removeActorButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                // TODO list linking
                eventManager.removeActor( activeScene);
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
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                saveNovel();
                super.clicked(event, x, y);
            }

        });
        return LibGdxConvenience.surroundWithInvisibleBorder(saveButton);
    }

    private void createScrollBorder(final ScrollPane scroll) {
        Border scrollBorder = new Border();
        scrollBorder.setSize(actorList.getWidth(), getHeight()
                * SCROLL_BORDER_HEIGHT_RATIO);
        scrollBorder.setActor(scroll);
        leftVerticalGroup.addActor(scrollBorder);
    }

    private void createSetSceneBackgroundButton() {
        setSceneBackgroundButton = new TextButton("Set background", uiSkin);
        leftVerticalGroup.addActor(setSceneBackgroundButton);

        setSceneBackgroundButton.addListener(new SetSceneBackgroundClickListener());
    }

    protected void hideButtonBorders() {
        for (Border border : toolButtonBorders) {
            border.setLineSize(0);
        }
    }

    protected void openNovel() {
        filePicker.openFileDialog(new FilePickerListener() {
            @Override
            public void fileOpened(final File selectedFile) {
                eventManager.fileOpen(
                        selectedFile);
            }

            @Override
            public void fileSaved(final File selectedFile) {
                // TODO Auto-generated method stub

            }


        }, new FileNameExtensionFilter(VISUALISTA_FILE_DESCRIPTION,
                VISUALISTA_FILE_FORMAT_WITHOUT_DOT));
    }

    private void resizeButtonGroup1Buttons(final Actor saveButtonBorder,
            final Actor openButtonBorder, final Actor cursorButtonBorder,
            final Actor arrowButtonBorder) {
        openButtonBorder.setSize(BUTTON_DIMENSION + BUTTON_EXTRA_WIDTH,
                BUTTON_DIMENSION);
        saveButtonBorder.setSize(BUTTON_DIMENSION + BUTTON_EXTRA_WIDTH,
                BUTTON_DIMENSION);
        cursorButtonBorder.setSize(BUTTON_DIMENSION + BUTTON_EXTRA_WIDTH,
                BUTTON_DIMENSION);
        arrowButtonBorder.setSize(BUTTON_DIMENSION + BUTTON_EXTRA_WIDTH,
                BUTTON_DIMENSION);
    }

    protected void saveNovel() {
        filePicker.saveFileDialog(new FilePickerListener(){
            @Override
            public void fileOpened(final File selectedFile) {
                // TODO Auto-generated method stub

            }

            @Override
            public void fileSaved(final File selectedFile) {
                eventManager.fileSave(
                        selectedFile);

            }
        },
        new FileNameExtensionFilter(VISUALISTA_FILE_DESCRIPTION,
                VISUALISTA_FILE_FORMAT_WITHOUT_DOT));
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    public void resize() {
        setSize(LeftBorder.LEFT_BORDER_WIDTH_RATIO * stage.getWidth(),
                LeftBorder.LEFT_BORDER_HEIGHT_RATIO * stage.getHeight());
        setPosition(
                LeftBorder.LEFT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                LeftBorder.LEFT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        setLineSize(LeftBorder.LEFT_BORDER_LINE_SIZE);
        setColor(LeftBorder.LEFT_BORDER_COLOR);

    }

    class SetSceneBackgroundClickListener extends ClickListener{


        @Override
        public void clicked(final InputEvent event, final float x,
                final float y) {
            // TODO selected scene and image
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Chose image", "png");
            filePicker.openFileDialog(new FilePickerListener() {

                @Override
                public void fileOpened(final File selectedFile) {
                    if (selectedFile != null) {
                        eventManager.changeSceneImage( activeScene,
                                selectedFile);
                    }
                }

                @Override
                public void fileSaved(final File selectedFile) {
                }
            }, filter);

            super.clicked(event, x, y);


        }
    }

}
