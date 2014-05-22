package io.github.visualista.visualista.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.IEditorView;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetGrid;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.util.BiDiMap;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorView implements ApplicationListener, IEditorView,
        FilePickerListener {

    // Declaring static variables //
    private static final float UPPER_BORDER_HEIGHT_RATIO = 0.05f;
    private static final float UPPER_BORDER_WIDTH_RATIO = 0.5f;
    private static final float UPPER_BORDER_X_DISPLACEMENT_RATIO = 0.25f;
    private static final float UPPER_BORDER_Y_DISPLACEMENT_RATIO = 0.95f;
    private static final Color UPPER_BORDER_COLOR = Color.BLACK;
    private static final int UPPER_BORDER_LINE_SIZE = 1;
    private static final float HIDDEN_SCENE_HEIGHT_RATIO = 0.2f;
    private static final float HIDDEN_SCENE_WIDTH_RATIO = 0.2f;
    private static final float HIDDEN_SCENE_X_DISPLACEMENT_RATIO = 0.7f;
    private static final float HIDDEN_SCENE_Y_DISPLACEMENT_RATIO = 0.7f;

    private static final float LOWER_BORDER_HEIGHT_RATIO = 2f / 10;
    private static final float LOWER_BORDER_WIDTH_RATIO = 5f / 10;
    private static final float LOWER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float LOWER_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final Color LOWER_BORDER_COLOR = Color.BLACK;
    private static final int LOWER_BORDER_LINE_SIZE = 1;

    private static final float LEFT_BORDER_WIDTH_RATIO = 2.5f / 10;
    private static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    private static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    private static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final Color LEFT_BORDER_COLOR = Color.BLACK;
    private static final int LEFT_BORDER_LINE_SIZE = 1;

    private static final float RIGHT_BORDER_WIDTH_RATIO = 2.5f / 10;
    private static final float RIGHT_BORDER_HEIGHT_RATIO = 1f;
    private static final float RIGHT_BORDER_X_DISPLACEMENT_RATIO = 7.5f / 10;
    private static final float RIGHT_BORDER_Y_DISPLACEMENT_RATIO = 0;
    private static final Color RIGHT_BORDER_COLOR = Color.BLACK;
    private static final int RIGHT_BORDER_LINE_SIZE = 1;

    private static final float CENTER_BORDER_WIDTH_RATIO = 5f / 10;
    private static final float CENTER_BORDER_HEIGHT_RATIO = 0.75f;
    private static final float CENTER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float CENTER_BORDER_Y_DISPLACEMENT_RATIO = 2f / 10;
    private static final Color CENTER_BORDER_COLOR = Color.BLACK;
    private static final int CENTER_BORDER_LINE_SIZE = 1;
    // End static variables //

    private Stage stage;

    private Skin uiSkin;

    private java.util.List<Actor> focusableActors;

    private Label actionLabel;

    private List<IAction> actionList;

    private RightBorder rightBorder;
    private UpperBorder upperBorder;
    private LowerBorder lowerBorder;
    private CenterBorder centerBorder;
    private VerticalGroup rightVerticalGroup;
    private VerticalGroup centerVerticalGroup;

    private final ViewEventManager eventManager = new ViewEventManager();

    private Dimension configDimension;

    private boolean isReady;

    private IFilePicker filePicker;

    private IGetScene activeScene;

    private TextArea sceneTextArea;

    private Image sceneBackgroundImage;

    private Border centerVerticalGroupBorder;

    private Tab editingTab;

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
        rightBorder = new RightBorder();
        stage.addActor(rightBorder);
        lowerBorder = new LowerBorder(stage, eventManager);
        stage.addActor(lowerBorder);
        centerBorder = new CenterBorder();
        stage.addActor(centerBorder);
        upperBorder = new UpperBorder(stage);
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
    public void addActor(IGetActor updatedActor) {
        leftBorder.addNewActor(updatedActor);
    }

    private class LowerBorder extends Border implements Updateable {

        private ViewEventManager eventManeger;
        private TextArea textArea;

        public LowerBorder(Stage stage, ViewEventManager eventManeger) {
            resizeLowerBorder();
            setActor(createLowerBorderContent());
            resizeLowerBorder();
            this.eventManeger = eventManeger;
            stage.addActor(this);
        }

        public void changeActiveScene(IGetScene scene) {
            textArea.setText(scene.getStoryText());
        }

        private void resizeLowerBorder() {
            setSize(LOWER_BORDER_WIDTH_RATIO * stage.getWidth(),
                    LOWER_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(LOWER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    LOWER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(LOWER_BORDER_LINE_SIZE);
            setColor(LOWER_BORDER_COLOR);

        }

        private TextArea createLowerBorderContent() {
            textArea = new TextArea("", uiSkin);

            textArea.addCaptureListener(new FocusListener() {

                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor,
                        boolean focused) {
                    if (!focused) {
                        eventManager.fireViewEvent(this,
                                Type.CHANGE_SCENE_TEXT, activeScene,
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

    private class UpperBorder extends Border implements Updateable, TabListener {

        private List<Tab> hiddenSceneList;

        private BiDiMap<Tab, IGetScene> tabs;

        private HorizontalGroup sceneButtonGroup;

        private HorizontalGroup tabUtilityButtons;

        public UpperBorder(Stage stage) {
            tabs = new BiDiMap<Tab, IGetScene>();
            this.setActor(createUpperBorderContent());
            resizeUpperBorder();
            stage.addActor(this);
        }

        public void changeActiveScene(IGetScene scene) {
            Tab tab = tabs.getKey(scene);
            bringTabToTabGroup(tab);
            for (Tab aTab : tabs.getAllKeys()) {
                aTab.useSelectStyle(false);
            }
            tab.useSelectStyle(true);
            // tabs.put(tab, scene);
            // hideOverFlowingScenes();
            // fillGridFromScene(scene);

        }

        private void bringTabToTabGroup(Tab tab) {
            int index = hiddenSceneList.getItems().indexOf(tab, true);
            if (index != -1) {
                hiddenSceneList.getItems().removeIndex(index);
                sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
            }

        }

        public void addNewScene(IGetScene scene) {
            String name = getPaddedSceneName(scene);
            Tab tab = new Tab(name, uiSkin);
            tab.addTabListener(this);
            tab.setHeight(upperBorder.getHeight());
            sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
            tabs.put(tab, scene);

        }

        public void updateScene(IGetScene scene) {

            String name = getPaddedSceneName(scene);
            tabs.getKey(scene).setText(name);
        }

        private String getPaddedSceneName(IGetScene scene) {
            String name = scene.getName();
            if (name == null) {
                name = "";
            }
            while (name.length() < 5) {
                name += " ";
            }
            return name;
        }

        public void clearSceneTabs() {
            if (tabs != null) {
                tabs.clear();
            }
            if (hiddenSceneList != null) {
                hiddenSceneList.getItems().clear();
            }
        }

        @Override
        public void tabEvent(Tab source,
                io.github.visualista.visualista.view.TabListener.EventType type) {
            if (type == EventType.SELECT) {
                if (activeScene == tabs.getValue(source)) {
                    source.makeNameEditable();
                    source.giveFocusFrom(stage);
                    editingTab = source;
                } else {
                    eventManager.fireViewEvent(this, Type.SELECT_SCENE,
                            tabs.getValue(source));
                }
            } else if (type == EventType.NAME_CHANGE) {
                eventManager.fireViewEvent(this, Type.CHANGE_SCENE_NAME,
                        tabs.getValue(source), source.newName());
            }

        }

        private HorizontalGroup createUpperBorderContent() {

            // Declaring and defining local variables //
            final HorizontalGroup upperBorderContentWrapper = new HorizontalGroup();
            hiddenSceneList = createHiddenSceneList();
            final ScrollPane hiddenSceneDropDown = createHiddenSceneDropDown(hiddenSceneList);
            final TextButton newSceneButton = createNewSceneButton();
            final TextButton overflowDropdownButton = createSceneOverflowButton(hiddenSceneDropDown);
            tabUtilityButtons = createTabUtilityButtons(newSceneButton,
                    overflowDropdownButton);
            sceneButtonGroup = new HorizontalGroup();
            // End defining local variables //

            sceneButtonGroup.addActor(tabUtilityButtons);

            upperBorderContentWrapper.addActor(sceneButtonGroup);
            sceneButtonGroup.setX(this.getLineSize()); // Makes sure the border
                                                       // lines does not
                                                       // collide!

            upperBorderContentWrapper.addActor(hiddenSceneDropDown);
            return upperBorderContentWrapper;
        }

        private TextButton createNewSceneButton() {
            TextButton newButton = new TextButton("+", uiSkin);
            newButton.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x,
                        float y) {
                    eventManager.fireViewEvent(this, Type.NEW_SCENE);
                }
            });
            return newButton;
        }

        private TextButton createSceneOverflowButton(
                final ScrollPane dropDownScenes) {
            TextButton newButton = new TextButton(">", uiSkin);
            newButton.setVisible(false);
            newButton.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x,
                        float y) {
                    dropDownScenes.setVisible(true);
                }
            });
            return newButton;
        }

        private HorizontalGroup createTabUtilityButtons(
                final TextButton... utilityButtons) {
            HorizontalGroup newGroup = new HorizontalGroup();
            for (TextButton button : utilityButtons) {
                newGroup.addActor(button);
            }

            return newGroup;

        }

        private ScrollPane createHiddenSceneDropDown(List<Tab> hiddenScenes) {
            ScrollPane newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
            newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
            newScrollPane.setFadeScrollBars(false);
            newScrollPane.setWidth(100);// actionList.getWidth());
            newScrollPane.setPosition(stage.getWidth()
                    * HIDDEN_SCENE_X_DISPLACEMENT_RATIO, stage.getHeight()
                    * HIDDEN_SCENE_Y_DISPLACEMENT_RATIO);

            newScrollPane.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x,
                        float y) {
                    // Debug Code //
                    Gdx.app.log("Hidden Scenes Dropdown", "Clicked");
                    // End Debug //
                }
            });
            newScrollPane.setVisible(false);
            return newScrollPane;
        }

        private ArrayList<Tab> fixOverFlowingScenes(
                HorizontalGroup sceneButtonGroup,
                List<Tab> currentlyHiddenScenes) {
            ArrayList<Tab> newTablist = new ArrayList<Tab>();
            ArrayList<Tab> removedActors = new ArrayList<Tab>();
            for (Actor tab : currentlyHiddenScenes.getItems()) {
                sceneButtonGroup.addActor(tab);
            }
            SnapshotArray<Actor> children = sceneButtonGroup.getChildren();
            if (children.size <= 0) {
                return newTablist;
            }

            float currentWidthOfTabs = 0;
            Tab tabToRemove = (Tab) children.get(children.size - 1);

            Iterator<Actor> it = children.iterator();
            while (it.hasNext()) {
                currentWidthOfTabs += it.next().getWidth();
            }

            while (currentWidthOfTabs > sceneButtonGroup.getWidth()
                    && children.size > 1) {
                sceneButtonGroup.removeActor(tabToRemove);
                removedActors.add(tabToRemove);
                tabToRemove = (Tab) children.get(children.size - 1);
            }
            currentlyHiddenScenes.setItems((Tab[]) removedActors.toArray());

            return newTablist;

        }

        private List<Tab> createHiddenSceneList() {
            List<Tab> newList = new List<Tab>(uiSkin);
            newList.setWidth(150);
            newList.setColor(Color.BLACK);
            return newList;
        }

        private void resizeUpperBorder() {
            setSize(UPPER_BORDER_WIDTH_RATIO * stage.getWidth(),
                    UPPER_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(UPPER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    UPPER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(UPPER_BORDER_LINE_SIZE);
            setColor(UPPER_BORDER_COLOR);
        }

        @Override
        public void update() {
            // TODO Auto-generated method stub

        }

    }

    private class CenterBorder extends Border implements Updateable {
        private IGetActor selectedActor;
        private ArrayList<Border> borders = new ArrayList<Border>();
        private Dimension gridDimensions;

        public CenterBorder() {
            resizeCenterBorder();
            createCenterEditorBorderContent();
            resizeCenterBorder();
        }

        public void updateScene(IGetScene scene) {
            fillGridFromScene(scene);
        }

        public void addNewScene(IGetScene newScene) {
            fillGridFromScene(newScene);
        }

        public void chaneActiveScene(IGetScene scene) {
            fillGridFromScene(scene);
        }

        public void selectActor(IGetActor actor) {
            selectedActor = actor;

        }

        private void createCenterEditorBorderContent() {
            centerVerticalGroup = new VerticalGroup();

            sceneBackgroundImage = new Image();
            Stack stack = new Stack();
            stack.add(sceneBackgroundImage);

            centerVerticalGroupBorder = new Border();
            centerVerticalGroupBorder.setActor(centerVerticalGroup);
            stack.add(centerVerticalGroupBorder);

            this.setActor(stack);
        }

        private void fillGridFromScene(IGetScene scene) {
            Matrix<Image> gridButtons = createImagesForGrid(scene);
            centerVerticalGroup.clearChildren();
            fillGrid(centerVerticalGroup, gridButtons);
            resizeCenterBorder();
        }

        private Matrix<Image> createImagesForGrid(IGetScene scene) {
            Matrix<Image> gridButtons = new Matrix<Image>(scene.getIGetGrid()
                    .getSize());
            IGetGrid grid = scene.getIGetGrid();
            int gridWidth = grid.getSize().getWidth();
            int gridHeight = grid.getSize().getHeight();

            for (int y = 0; y < gridHeight; ++y) {
                for (int x = 0; x < gridWidth; ++x) {
                    final IGetTile tileAtCurrentPosition = grid
                            .getAt(new Point(x, y));
                    final Image imageForCurrentTile = ModelToGdxHelper
                            .createImageFor(tileAtCurrentPosition);
                    imageForCurrentTile.addCaptureListener(new ClickListener() {

                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (selectedActor != null) {
                                if (selectedTool == EditorTool.ARROW) {

                                    eventManager.fireViewEvent(
                                            imageForCurrentTile,
                                            Type.TILE_SET_ACTOR,
                                            tileAtCurrentPosition,
                                            selectedActor);

                                } else if (selectedTool == EditorTool.CURSOR) {
                                    eventManager.fireViewEvent(this,
                                            Type.SELECT_TILE,
                                            tileAtCurrentPosition);
                                }
                            }
                            super.clicked(event, x, y);
                        }

                    });
                    gridButtons.setAt(new Point(x, y), imageForCurrentTile);
                }
            }
            return gridButtons;
        }

        /*
         * Old resizeCenterBorder private void resizeCenterBorder() {
         * centerBorder.setSize( horizontalDistanceBetween(leftBorder,
         * rightBorder), (upperBorder.getY() - lowerBorder.getY() - lowerBorder
         * .getHeight())); centerBorder.setPosition(rightSideOf(leftBorder),
         * lowerBorder.getY() + lowerBorder.getHeight());
         * centerVerticalGroupBorder.setLineSize(0);
         * centerVerticalGroupBorder.setSize(centerBorder.getWidth(),
         * centerBorder.getHeight());
         * 
         * }
         */

        public final void fillGrid(VerticalGroup group, IMatrixGet<Image> data) {
            float buttonLength = calculateBorderLength(group.getWidth(),
                    group.getHeight(), data.getSize());
            gridDimensions = data.getSize();

            for (int i = 0; i < data.getSize().getHeight(); i++) {
                HorizontalGroup row = new HorizontalGroup();
                group.addActor(row);
                for (int j = 0; j < data.getSize().getWidth(); j++) {
                    Image button = data.getAt(new Point(i, j));
                    Border border = new Border();
                    border.setActor(button);
                    border.setSize(buttonLength, buttonLength);
                    border.setLineSize(1);
                    borders.add(border);
                    row.addActor(border);
                }
            }
        }

        private float calculateBorderLength(float width, float height,
                Dimension size) {
            float prefferedButtonHeight = height / size.getHeight();
            float prefferedButtonWidth = width / size.getWidth();
            float buttonLength = (float) Math.floor(Math.min(
                    prefferedButtonWidth, prefferedButtonHeight));
            return buttonLength;
        }

        private void resizeCenterBorder() {
            setSize(CENTER_BORDER_WIDTH_RATIO * stage.getWidth(),
                    CENTER_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(CENTER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    CENTER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(CENTER_BORDER_LINE_SIZE);
            setColor(CENTER_BORDER_COLOR);
            if (gridDimensions != null) {
                float buttonLength = calculateBorderLength(getWidth(),
                        getHeight(), gridDimensions);
                for (Border border : borders) {
                    border.setSize(buttonLength, buttonLength);
                }
            }
        }

        @Override
        public void update() {
            // TODO Auto-generated method stub

        }

    }

    private class RightBorder extends Border implements Updateable {
        private Image actorImage;
        private TextField actorField;
        private TextButton modifyButton;
        private TextButton addActionButton;
        private IGetActor selectedActor;
        private ArrayList<IGetScene> sceneList = new ArrayList<IGetScene>();

        public RightBorder() {
            resizeRightBorder();
            createRightEditorBorderContent();
            resizeRightBorder();

        }

        public void changeActiveScene(IGetScene scene) {
            rightVerticalGroup.setVisible(false);

        }

        public void addNewScene(IGetScene newScene) {
            sceneList.add(newScene);

        }

        public void updateActor(IGetActor updatedActor) {
            if (updatedActor == selectedActor) {
                setActorImage(ModelToGdxHelper.createDrawableFor(updatedActor));
                updateActionList(updatedActor);
            }

        }

        public void selectActor(IGetActor actor) {
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

        public void setActorImage(TextureRegionDrawable createDrawableFor) {
            actorImage.setDrawable(createDrawableFor);

        }

        private void resizeRightBorder() {
            setSize(RIGHT_BORDER_WIDTH_RATIO * stage.getWidth(),
                    RIGHT_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(RIGHT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    RIGHT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(RIGHT_BORDER_LINE_SIZE);
            setColor(RIGHT_BORDER_COLOR);
        }

        private void createRightEditorBorderContent() {
            rightVerticalGroup = new VerticalGroup();
            setActor(rightVerticalGroup);

            actorField = new TextField("", uiSkin);
            rightVerticalGroup.addActor(actorField);
            rightVerticalGroup.setVisible(false);

            actorField.addCaptureListener(new FocusListener() {

                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor,
                        boolean focused) {
                    if (!focused) {
                        eventManager.fireViewEvent(this,
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
            rightVerticalGroup.addActor(actorImageBorder);

            actorImage.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO selected scene and image
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "Select image (*.png)", "png");
                    filePicker.openFileDialog(new FilePickerListener() {

                        @Override
                        public void fileOpened(File selectedFile) {
                            eventManager.fireViewEvent(this,
                                    Type.CHANGE_ACTOR_IMAGE, selectedActor,
                                    selectedFile);
                        }

                        @Override
                        public void fileSaved(File selectedFile) {
                        }
                    }, filter);

                    super.clicked(event, x, y);
                }

            });

            actionLabel = new Label("Actions", uiSkin);
            actionLabel.setSize(50, 50);
            actionLabel.setColor(Color.BLACK);
            rightVerticalGroup.addActor(actionLabel);

            actionList = new List<IAction>(uiSkin);

            actionList.setWidth(getWidth() - getLineSize() * 4);
            actionList.setColor(Color.BLACK);

            final ScrollPane scroll = new ScrollPane(actionList, uiSkin);
            scroll.setFadeScrollBars(false);
            Border scrollBorder = new Border();
            scrollBorder.setLineOutsideActor(true);
            scrollBorder.setLineSize(1);
            scrollBorder.setSize(actionList.getWidth(), getHeight() * 0.7f);
            scrollBorder.setActor(scroll);
            rightVerticalGroup.addActor(scrollBorder);

            actionList.addCaptureListener(new EventListener() {

                @Override
                public boolean handle(Event event) {
                    if (event instanceof InputEvent) {
                    }
                    return false;
                }

            });

            modifyButton = new TextButton("Modify", uiSkin);
            modifyButton.setSize(150, 20);

            addActionButton = new TextButton("Add", uiSkin);
            addActionButton.setSize(150, 20);
            addActionButton.addCaptureListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Dialog[] dialogs = {
                            new ChangeSceneDialog(uiSkin, selectedActor,
                                    sceneList, eventManager),
                            new SetSceneTextDialog(uiSkin, selectedActor,
                                    eventManager) };
                    String[] dialogTexts = { "Set scene action",
                            "Set scene text action" };
                    Dialog dialog = new SelectActionTypeDialog(uiSkin,
                            selectedActor, dialogs, dialogTexts);
                    dialog.invalidateHierarchy();
                    dialog.invalidate();
                    dialog.layout();
                    dialog.show(stage);
                    super.clicked(event, x, y);
                }

            });
            HorizontalGroup buttonContainer = new HorizontalGroup();
            buttonContainer.addActor(modifyButton);
            buttonContainer.addActor(addActionButton);

            rightVerticalGroup.addActor(buttonContainer);
        }

        @Override
        public void update() {
            // TODO Auto-generated method stub

        }

        public void updateActionList(IGetActor updatedActor) {
            IAction[] updatedActionList = new IAction[updatedActor.getActions()
                    .size()];
            actionList.setItems(updatedActor.getActions().toArray(
                    updatedActionList));
        }
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
