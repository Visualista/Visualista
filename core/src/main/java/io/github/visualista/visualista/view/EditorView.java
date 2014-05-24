package io.github.visualista.visualista.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.IEditorView;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.util.Dimension;

import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * View class for the Editor view, responsible for all the visual logic and
 * painting with help from libGdx.
 * 
 * @author Markus Bergland, Erik Risfelt, Pierre Krafft
 */

public class EditorView implements ApplicationListener, IEditorView,
        FilePickerListener {

    private static final float HIDDEN_SCENE_WIDTH_RATIO = 0.2f;
    private static final float LEFT_BORDER_WIDTH_RATIO = 2.5f / 10;
    private static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    private static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    private static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final Color LEFT_BORDER_COLOR = Color.BLACK;
    private static final int LEFT_BORDER_LINE_SIZE = 1;

    Stage stage;

    Skin uiSkin;

    private java.util.List<Actor> focusableActors;

    Label actionLabel;

    List<IAction> actionList;

    private RightBorder rightBorder;
    UpperBorder upperBorder;
    private LowerBorder lowerBorder;
    private CenterBorder centerBorder;
    VerticalGroup rightVerticalGroup;
    VerticalGroup centerVerticalGroup;

    final ViewEventManager eventManager = new ViewEventManager();

    private Dimension configDimension;

    private boolean isReady;

    IFilePicker filePicker;

    IGetScene activeScene;

    private TextArea sceneTextArea;

    Border centerVerticalGroupBorder;

    Tab editingTab;

    private boolean actorFieldHasFocus;

    protected EditorTool selectedTool;

    private final ArrayList<Border> toolButtonBorders;

    private LeftBorder leftBorder;

    protected boolean sceneTextAreaHasFocus;

    public EditorView(Dimension dimension, IFilePicker filePicker) {
        this.configDimension = dimension;

        this.filePicker = filePicker;
        toolButtonBorders = new ArrayList<Border>();
    }

    @Override
    public final void create() {
        stage = new Stage();
        stage.getViewport().setWorldHeight(configDimension.getHeight());
        stage.getViewport().setWorldWidth(configDimension.getWidth());

        stage.clear();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        leftBorder = new LeftBorder();
        stage.addActor(leftBorder);
        rightBorder = new RightBorder(this);
        stage.addActor(rightBorder);
        lowerBorder = new LowerBorder(this, stage, eventManager);
        stage.addActor(lowerBorder);
        centerBorder = new CenterBorder(this);
        stage.addActor(centerBorder);
        upperBorder = new UpperBorder(this, stage);
        stage.addActor(upperBorder);

        focusableActors = new java.util.ArrayList<Actor>();
        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {
                EditorView.this.objectHit(stage.hit(x, y, true));
                return false;
            }

        });

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(true);

        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
    }

    // Create Editor //

    private void objectHit(Actor hitObject) {
        stage.setKeyboardFocus(hitObject);
        stage.setScrollFocus(hitObject);
        // TODO hide objects that want that
    }

    private void addFocusableActorField(Actor actorField) {
        focusableActors.add(actorField);
    }

    private Border surroundWithInvisibleBorder(Actor actor) {
        Border surroundingBorder = new Border();
        surroundingBorder.setLineSize(0);
        surroundingBorder.setActor(actor);// TODO Auto-generated method stub
        return surroundingBorder;
    }

    protected void saveNovel() {
        filePicker.saveFileDialog(this, new FileNameExtensionFilter(
                "Visualista Novel", "vis"));
    }

    protected void openNovel() {
        filePicker.openFileDialog(EditorView.this, new FileNameExtensionFilter(
                "Visualista Novel", "vis"));
    }

    @Override
    public boolean getIsReady() {
        return isReady;
    }

    private float rightSideOf(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return actor.getX() + actor.getWidth();
    }

    @Override
    public void resize(final int width, final int height) {
        // stage.setViewport(configDimension.getWidth(),
        // configDimension.getHeight(), true);
        // stage.getCamera().translate(-stage.getGutterWidth(),
        // -stage.getGutterHeight(), 0);

    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void addViewEventListener(ViewEventListener eventListener) {
        eventManager.addEventListener(eventListener);
    }

    public void removeViewEventListener(ViewEventListener eventListener) {
        eventManager.removeEventListener(eventListener);
    }

    @Override
    public void addScene(IGetScene newScene) {
        upperBorder.addNewScene(newScene);
        rightBorder.addNewScene(newScene);
        centerBorder.addNewScene(newScene);

    }

    @Override
    public void updateScene(IGetScene scene) {
        upperBorder.updateScene(scene);
        centerBorder.updateScene(scene);
        rightBorder.updateScene(scene);
        /*
         * String name = scene.getName(); String text = scene.getStoryText(); if
         * (name == null) { name = ""; } while (name.length() < 5) { name +=
         * " "; } if (text == null) { text = ""; } Gdx.app.log("Scene text",
         * text);
         * 
         * if (scene.getImage() != null) { final Drawable tile = new
         * TextureRegionDrawable(new TextureRegion( new
         * Texture(Gdx.files.absolute(scene.getImage().getFile()
         * .getAbsolutePath()))));
         * 
         * sceneBackgroundImage.setDrawable(tile); } else {
         * sceneBackgroundImage.setDrawable(null); } Tab tab = new Tab(name,
         * uiSkin); tab.addClickListener(this);
         * tab.setHeight(upperBorder.getHeight()); Tab oldTab =
         * tabs.getKey(scene); if (oldTab == null || true) {
         * sceneButtonGroup.addActorBefore(tabUtilityButtons, tab); } else {
         * sceneButtonGroup.addActorBefore(oldTab, tab);
         * sceneButtonGroup.removeActor(oldTab); } tabs.put(tab, scene);
         * hideOverFlowingScenes();
         * 
         * String[] actorNames = new String[scene.getActorsInScene().size()];
         * for (int i = 0; i < scene.getActorsInScene().size(); i++) {
         * actorNames[i] = scene.getActorsInScene().get(i).getName(); }
         * IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
         * selectedActor = null;
         * leftBorder.actorList.setItems(scene.getActorsInScene
         * ().toArray(temp)); sceneTextArea.setText(text);
         */
    }

    private static float horizontalDistanceBetween(
            com.badlogic.gdx.scenes.scene2d.Actor actor1,
            com.badlogic.gdx.scenes.scene2d.Actor actor2) {
        float distance = actor2.getX() - actor1.getX() - actor1.getWidth();
        if (distance >= 0)
            return distance;
        {
        }
        distance = actor1.getX() - actor2.getX() - actor2.getWidth();
        if (distance >= 0) {
            return distance;
        } else {
            return 0;
        }
    }

    @Override
    public void removeScene(IGetScene scene) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeActiveScene(IGetScene scene) {
        /*
         * updateScene(scene); String name = scene.getName(); if (name == null)
         * { name = ""; } while (name.length() < 5) { name += " "; } Tab tab =
         * new Tab(name, uiSkin); tab.addClickListener(this);
         * tab.setHeight(upperBorder.getHeight()); Tab oldTab =
         * tabs.getKey(scene); if (oldTab != null) { if
         * (hiddenSceneList.getItems().contains(oldTab, true)) {
         * sceneButtonGroup.addActorAt(0, tab);
         * hiddenSceneList.getItems().removeValue(oldTab, true); } else {
         * sceneButtonGroup.addActorBefore(oldTab, tab);
         * sceneButtonGroup.removeActor(oldTab); } } else {
         * sceneButtonGroup.addActorAt(0, tab); } for (Tab aTab :
         * tabs.getAllKeys()) { aTab.useSelectStyle(false); }
         * tab.useSelectStyle(true); tabs.put(tab, scene);
         * hideOverFlowingScenes(); fillGridFromScene(scene);
         */
        // TODO make sure that the order of operation does not matter. Right
        // border will dissapear if its changeActive scene is called after left
        // borders
        upperBorder.changeActiveScene(scene);
        centerBorder.chaneActiveScene(scene);
        rightBorder.changeActiveScene(scene);
        leftBorder.changeActiveScene(scene);
        lowerBorder.changeActiveScene(scene);
        activeScene = scene;

    }

    @Override
    public void changeActiveNovel(IGetNovel updatedNovel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fileOpened(File selectedFile) {
        eventManager.fireViewEvent(this, Type.FILE_OPEN, null, selectedFile);
    }

    @Override
    public void fileSaved(File selectedFile) {
        eventManager.fireViewEvent(this, Type.FILE_SAVE, null, selectedFile);

    }

    @Override
    public void clearModel() {
        clearSceneTabs();
        clearActorList();
        clearActionList();
    }

    private void clearActionList() {
        // actionList.setItems(new String[] {});
    }

    private void clearActorList() {
        // actorList.setItems(new String[] {});

    }

    private void clearSceneTabs() {
        upperBorder.clearSceneTabs();

    }

    @Override
    public void selectActor(IGetActor actor) {
        rightBorder.selectActor(actor);
        centerBorder.selectActor(actor);
    }

    @Override
    public void updateActor(IGetActor updatedActor) {
        rightBorder.updateActor(updatedActor);
    }

    @Override
    public void updateTile(Object updatedObject, IGetTile updatedTile) {
        ((Image) updatedObject).setDrawable(ModelToGdxHelper
                .createDrawableFor(updatedTile));

    }

    @Override
    public void addNewActor(IGetActor actor) {
        leftBorder.addNewActor(actor);
        rightBorder.addNewActor(actor);
    }

    private class LeftBorder extends Border implements Updateable {

        private TextButton addActorButton;
        private TextButton removeActorButton;

        private TextButton setSceneBackgroundButton;

        private VerticalGroup leftVerticalGroup;
        private List<IGetActor> actorList;

        public LeftBorder() {
            resizeLeftBorder();
            createLeftBorderContent();
        }

        public void changeActiveScene(IGetScene scene) {
            IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
            actorList.setItems(new Array<IGetActor>(scene.getActorsInScene()
                    .toArray(temp)));
            if (actorList.getSelected() != null) {
                eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                        actorList.getSelected());
            }
        }

        public void addNewActor(IGetActor updatedActor) {
            actorList.getItems().add(updatedActor);
            actorList.setSelectedIndex((leftBorder.actorList.getItems()
                    .indexOf(updatedActor, true)));

        }

        private void resizeLeftBorder() {
            setSize(LEFT_BORDER_WIDTH_RATIO * stage.getWidth(),
                    LEFT_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(LEFT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    LEFT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(LEFT_BORDER_LINE_SIZE);
            setColor(LEFT_BORDER_COLOR);
        }

        private List<IGetActor> createActorList() {
            final List<IGetActor> list = new List<IGetActor>(uiSkin);

            list.setWidth(getWidth() - getLineSize() * 4);
            list.setColor(Color.BLACK);
            list.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int index = list.getSelectedIndex();
                    if (index != -1) {
                        eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
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
            final ScrollPane scroll = new ScrollPane(actorList, uiSkin);
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
            removeActorButton = new TextButton("Remove actor", uiSkin);
            removeActorButton.setSize(150, 20);

            removeActorButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO list linking
                    eventManager.fireViewEvent(this, Type.REMOVE_ACTOR,
                            activeScene);
                }
            });
        }

        // End create Editor //

        private void createAddActorButton() {
            addActorButton = new TextButton("Add actor", uiSkin);
            addActorButton.setSize(150, 20);

            addActorButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    eventManager.fireViewEvent(this, Type.NEW_ACTOR,
                            activeScene);
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
            final Border border = surroundWithInvisibleBorder(arrowButton);
            border.setLineOutsideActor(true);
            border.setLineSize(1);
            selectedTool = EditorTool.ARROW;
            arrowButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedTool = EditorTool.ARROW;
                    hideButtonBorders();
                    border.setLineSize(1);
                }

            });
            toolButtonBorders.add(border);
            return border;
        }

        protected void hideButtonBorders() {
            for (Border border : toolButtonBorders) {
                border.setLineSize(0);
            }
        }

        private Actor createCursorButton() {
            final Drawable cursor = new TextureRegionDrawable(
                    new TextureRegion(new Texture(
                            Gdx.files.internal("icons/cursor.png"))));
            ImageButton cursorButton = new ImageButton(cursor);
            final Border border = surroundWithInvisibleBorder(cursorButton);
            border.setLineOutsideActor(true);
            cursorButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedTool = EditorTool.CURSOR;
                    hideButtonBorders();
                    border.setLineSize(1);
                }

            });
            toolButtonBorders.add(border);
            return border;
        }

        private Actor createSaveButton() {
            final Drawable save = new TextureRegionDrawable(new TextureRegion(
                    new Texture(Gdx.files.internal("icons/save.png"))));
            ImageButton saveButton = new ImageButton(save);
            saveButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    saveNovel();
                    super.clicked(event, x, y);
                }

            });
            return surroundWithInvisibleBorder(saveButton);
        }

        private Actor createOpenButton() {
            final Drawable open = new TextureRegionDrawable(new TextureRegion(
                    new Texture(Gdx.files.internal("icons/open.png"))));
            ImageButton openButton = new ImageButton(open);
            openButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    openNovel();
                    super.clicked(event, x, y);
                }

            });
            return surroundWithInvisibleBorder(openButton);
        }

        private void createSetSceneBackgroundButton() {
            setSceneBackgroundButton = new TextButton("Set background", uiSkin);
            leftVerticalGroup.addActor(setSceneBackgroundButton);

            setSceneBackgroundButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO selected scene and image
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "Chose image", "png");
                    filePicker.openFileDialog(new FilePickerListener() {

                        @Override
                        public void fileOpened(File selectedFile) {
                            if (selectedFile != null) {
                                eventManager.fireViewEvent(this,
                                        Type.CHANGE_SCENE_IMAGE, activeScene,
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

    @Override
    public void updateActionList(IGetActor updatedActor) {
        rightBorder.updateActionList(updatedActor);

    }
}
