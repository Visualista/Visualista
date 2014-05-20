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

import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorView implements ApplicationListener, IEditorView,
FilePickerListener, TabClickListener {

    // Declaring static variables //
    private static final float UPPER_BORDER_HEIGHT_RATIO = 2f / 10;
    private static final float UPPER_BORDER_WIDTH_RATIO = 5f / 10;
    private static final float UPPER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float UPPER_BORDER_Y_DISPLACEMENT_RATIO = 8f / 10;
    private static final Color UPPER_BORDER_COLOR = Color.BLACK;
    private static final int UPPER_BORDER_LINE_SIZE = 1;

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
    private static final float CENTER_BORDER_HEIGHT_RATIO = 6f / 10;
    private static final float CENTER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float CENTER_BORDER_Y_DISPLACEMENT_RATIO = 2f / 10;
    private static final Color CENTER_BORDER_COLOR = Color.BLACK;
    private static final int CENTER_BORDER_LINE_SIZE = 1;
    // End static variables //
    
    private Stage stage;

    private Skin uiSkin;

    private java.util.List<Actor> focusableActors;

    private Label actionLabel;

    private Matrix<Image> gridButtons;

    private java.util.List<Border> contents;
    
    private List<IAction> actionList;

    private RightBorder rightBorder;
    private UpperBorder upperBorder;
    private LowerBorder lowerBorder;
    private CenterBorder centerBorder;
    private VerticalGroup rightVerticalGroup;
    private VerticalGroup centerVerticalGroup;

    private HorizontalGroup sceneButtonGroup;

    private final ViewEventManager eventManager = new ViewEventManager();

    private Dimension configDimension;

    private BiDiMap<Tab, IGetScene> tabs;


    private HorizontalGroup tabUtilityButtons;

    private boolean isReady;

    private IFilePicker filePicker;

    private IGetScene activeScene;

    private TextArea sceneTextArea;

    private Image sceneBackgroundImage;

    private Border centerVerticalGroupBorder;


    private Tab editingTab;

    private IGetActor selectedActor;

    private boolean actorFieldHasFocus;

    protected EditorTool selectedTool;

    private final ArrayList<Border> toolButtonBorders;

    private LeftBorder leftBorder;

    protected boolean sceneTextAreaHasFocus;

    public EditorView(Dimension dimension, IFilePicker filePicker) {
        this.configDimension = dimension;
        tabs = new BiDiMap<Tab, IGetScene>();
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
        rightBorder = new RightBorder();
        lowerBorder = new LowerBorder();
        centerBorder = new CenterBorder();
        upperBorder = new UpperBorder();
        contents.add(leftBorder);
        contents.add(rightBorder);
        contents.add(lowerBorder);
        contents.add(centerBorder);
        contents.add(upperBorder);
        
        focusableActors = new java.util.ArrayList<Actor>();

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(false);

        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
    }

    // Create Editor //
    private ScrollPane createScrollPane() {
        final ScrollPane scroll = new ScrollPane(leftBorder.actorList, uiSkin);
        scroll.setFadeScrollBars(false);
        return scroll;
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

    private void fillGridFromScene(IGetScene scene) {
        centerBorder.createImagesForGrid(scene);
        centerVerticalGroup.clearChildren();
        centerBorder.fillGrid(centerVerticalGroup, gridButtons);
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
        updateScene(newScene);

    }

    private void hideOverFlowingScenes() {
        SnapshotArray<Actor> children = sceneButtonGroup.getChildren();
        overflowDropdownButton.setVisible(false);
        float totalWidth = 0;
        Actor firstChild = null;
        if (children.size > 0) {
            firstChild = children.get(0);
        }
        for (Actor overflowedTab : hiddenSceneList.getItems()) {
            sceneButtonGroup.addActorBefore(firstChild, overflowedTab);
        }
        hiddenSceneList.setItems();

        for (com.badlogic.gdx.scenes.scene2d.Actor child : children) {
            totalWidth += child.getWidth();
        }
        ArrayList<Tab> temp = new ArrayList<Tab>();
        while (totalWidth > sceneButtonGroup.getWidth()
                && sceneButtonGroup.getChildren().size > 1) {
            Actor currentlyFirstChild = sceneButtonGroup.getChildren().get(0);
            totalWidth -= currentlyFirstChild.getWidth();
            sceneButtonGroup.removeActor(currentlyFirstChild);
            overflowDropdownButton.setVisible(true);
            temp.add((Tab) currentlyFirstChild);
        }
        Tab[] temp2 = new Tab[temp.size()];
        hiddenSceneList.setItems(temp.toArray(temp2));
        hiddenSceneList.setVisible(true);
    }

    @Override
    public void updateScene(IGetScene scene) {
        String name = scene.getName();
        String text = scene.getStoryText();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
            name += " ";
        }
        if (text == null) {
            text = "";
        }
        Gdx.app.log("Scene text", text);

        if (scene.getImage() != null) {
            final Drawable tile = new TextureRegionDrawable(new TextureRegion(
                    new Texture(Gdx.files.absolute(scene.getImage().getFile()
                            .getAbsolutePath()))));

            sceneBackgroundImage.setDrawable(tile);
        } else {
            sceneBackgroundImage.setDrawable(null);
        }
        Tab tab = new Tab(name, uiSkin);
        tab.addClickListener(this);
        tab.setHeight(upperBorder.getHeight());
        Tab oldTab = tabs.getKey(scene);
        if (oldTab == null || true) {
            sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
        } else {
            sceneButtonGroup.addActorBefore(oldTab, tab);
            sceneButtonGroup.removeActor(oldTab);
        }
        tabs.put(tab, scene);
        hideOverFlowingScenes();

        String[] actorNames = new String[scene.getActorsInScene().size()];
        for (int i = 0; i < scene.getActorsInScene().size(); i++) {
            actorNames[i] = scene.getActorsInScene().get(i).getName();
        }
        IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
        selectedActor = null;
        leftBorder.actorList.setItems(scene.getActorsInScene().toArray(temp));
        sceneTextArea.setText(text);

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
        updateScene(scene);
        String name = scene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
            name += " ";
        }
        Tab tab = new Tab(name, uiSkin);
        tab.addClickListener(this);
        tab.setHeight(upperBorder.getHeight());
        Tab oldTab = tabs.getKey(scene);
        if (oldTab != null) {
            if (hiddenSceneList.getItems().contains(oldTab, true)) {
                sceneButtonGroup.addActorAt(0, tab);
                hiddenSceneList.getItems().removeValue(oldTab, true);
            } else {
                sceneButtonGroup.addActorBefore(oldTab, tab);
                sceneButtonGroup.removeActor(oldTab);
            }
        } else {
            sceneButtonGroup.addActorAt(0, tab);
        }
        for (Tab aTab : tabs.getAllKeys()) {
            aTab.useSelectStyle(false);
        }
        tab.useSelectStyle(true);
        tabs.put(tab, scene);
        hideOverFlowingScenes();
        fillGridFromScene(scene);
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
        hiddenSceneList.getItems().clear();
        sceneButtonGroup.clearChildren();
        sceneButtonGroup.addActor(tabUtilityButtons);
        tabs.clear();
    }

    @Override
    public void tabEvent(Tab source,
            io.github.visualista.visualista.view.Tab.Type type) {
        if (type == Tab.Type.SELECT) {
            if (activeScene == tabs.getValue(source)) {
                source.makeNameEditable();
                editingTab = source;
            } else {
                eventManager.fireViewEvent(this, Type.SELECT_SCENE,
                        tabs.getValue(source));
            }
        }

    }

    @Override
    public void selectActor(IGetActor actor) {
        selectedActor = actor;
        if (actor != null) {
            rightVerticalGroup.setVisible(true);
            rightBorder.actorField.setText(actor.getName());
            rightBorder
            .setActorImage(ModelToGdxHelper.createDrawableFor(actor));
        } else {
            rightVerticalGroup.setVisible(false);
        }
    }

    @Override
    public void updateActor(IGetActor updatedActor) {
        if (updatedActor == selectedActor) {
            rightBorder.setActorImage(ModelToGdxHelper
                    .createDrawableFor(updatedActor));
        }
    }

    @Override
    public void updateTile(Object updatedObject, IGetTile updatedTile) {
        ((Image) updatedObject).setDrawable(ModelToGdxHelper
                .createDrawableFor(updatedTile));

    }

    @Override
    public void addActor(IGetActor updatedActor) {
        leftBorder.actorList.getItems().add(updatedActor);
        leftBorder.actorList.setSelectedIndex((leftBorder.actorList.getItems().indexOf(updatedActor,
                true)));
    }

    private class LowerBorder extends Border {

        private ViewEventManager eventManeger;
        
        public void LowerBorder(Stage stage, ViewEventManager eventManeger) {
            createLowerBorderContent();
            resizeLowerBorder();
            this.eventManeger = eventManeger;
            stage.addActor(this);
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
            final TextArea newTextArea = new TextArea("", uiSkin);

            newTextArea.addCaptureListener(new FocusListener() {

                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor,
                        boolean focused) {
                    if (!focused){
                        eventManager.fireViewEvent(this, Type.CHANGE_SCENE_TEXT, activeScene, newTextArea.getText());
                    }
                    super.keyboardFocusChanged(event, actor, focused);
                }


            });
            return newTextArea;
        }
    }

    private class UpperBorder extends Border {

        public void UpperBorder(Stage stage) {
            this.setActor(createUpperBorderContent());
            resizeUpperBorder();
            stage.addActor(this);
        }

        private HorizontalGroup createUpperBorderContent() {
            
            // Declaring and defining local variables //
            final HorizontalGroup upperBorderContentWrapper = new HorizontalGroup();
            final List<Tab> hiddenSceneList = createHiddenSceneList();
            final ScrollPane hiddenSceneDropDown = createHiddenSceneDropDown(hiddenSceneList);
            final TextButton newSceneButton = createNewSceneButton();
            final TextButton overflowDropdownButton = createSceneOverflowButton(hiddenSceneDropDown);
            final HorizontalGroup tabUtilityButtons = createTabUtilityButtons(newSceneButton, overflowDropdownButton);
            final HorizontalGroup sceneButtonGroup = new HorizontalGroup();
            // End defining local variables //
            
            sceneButtonGroup.addActor(tabUtilityButtons);

            upperBorderContentWrapper.addActor(sceneButtonGroup);
            sceneButtonGroup.setX(this.getLineSize()); // Makes sure the border lines does not collide!

            upperBorderContentWrapper.addActor(hiddenSceneDropDown);
            return upperBorderContentWrapper;
        }

        private TextButton createNewSceneButton(){
            TextButton newButton = new TextButton("+", uiSkin);
            newButton.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, float y) {
                    eventManager.fireViewEvent(this, Type.NEW_SCENE);
                }
            });
            return newButton;
        }

        private TextButton createSceneOverflowButton(final ScrollPane dropDownScenes){
            TextButton newButton = new TextButton(">", uiSkin);
            newButton.setVisible(false);
            newButton.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, float y) {
                    dropDownScenes.setVisible(true);
                }
            });
            return newButton;
        }

        private HorizontalGroup createTabUtilityButtons(final TextButton... utilityButtons){
            HorizontalGroup newGroup = new HorizontalGroup();
            for (TextButton button : utilityButtons){
                newGroup.addActor(button);
            }

            return newGroup;


        }

        private ScrollPane createHiddenSceneDropDown(List<Tab> hiddenScenes){
            ScrollPane newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
            newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
            newScrollPane.setFadeScrollBars(false);
            newScrollPane.setWidth(100);// actionList.getWidth());
            newScrollPane.setPosition(
                    rightBorder.getX() - hiddenSceneDropDown.getWidth(),
                    upperBorder.getY() - hiddenSceneDropDown.getHeight());

            newScrollPane.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, float y) {
                    // Debug Code //
                    Gdx.app.log("Hidden Scenes Dropdown", "Clicked");
                    // End Debug //
                }
            });
            newScrollPane.setVisible(false);
            return newScrollPane;
        }

        private List<Tab> createHiddenSceneList(){
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

    }

    private class CenterBorder extends Border {

        private HorizontalGroup centerBorder;

        public void CenterBorder() {
            resizeCenterBorder();
        }

        private void createCenterEditorBorderContent() {
            centerVerticalGroup = new VerticalGroup();
            stage.addActor(centerBorder);

            sceneBackgroundImage = new Image();
            Stack stack = new Stack();
            stack.add(sceneBackgroundImage);

            centerVerticalGroupBorder = new Border();
            centerVerticalGroupBorder.setActor(centerVerticalGroup);
            stack.add(centerVerticalGroupBorder);

            this.setActor(stack);
            gridButtons = new Matrix<Image>(new Dimension(5, 5));
        }

        private void createImagesForGrid(IGetScene scene) {
            gridButtons = new Matrix<Image>(scene.getIGetGrid().getSize());
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
            float prefferedButtonHeight = group.getHeight()
                    / data.getSize().getHeight();
            float prefferedButtonWidth = group.getWidth()
                    / data.getSize().getWidth();
            float buttonLength = (float) Math.floor(Math.min(
                    prefferedButtonWidth, prefferedButtonHeight));

            for (int i = 0; i < data.getSize().getHeight(); i++) {
                HorizontalGroup row = new HorizontalGroup();
                group.addActor(row);
                for (int j = 0; j < data.getSize().getWidth(); j++) {
                    Image button = data.getAt(new Point(i, j));
                    Border border = new Border();
                    border.setActor(button);
                    border.setSize(buttonLength, buttonLength);
                    border.setLineSize(1);
                    row.addActor(border);
                }
            }
        }

        private void resizeCenterBorder() {
            setSize(CENTER_BORDER_WIDTH_RATIO * stage.getWidth(),
                    CENTER_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(CENTER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    CENTER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(CENTER_BORDER_LINE_SIZE);
            setColor(CENTER_BORDER_COLOR);
        }

    }

    private class RightBorder extends Border {
        private Image actorImage;
        private TextField actorField;
        private TextButton modifyButton;
        private TextButton addActionButton;

        private Border rightBorder;

        public void RightBorder() {
            resizeRightBorder();
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
            rightBorder = new Border();
            stage.addActor(rightBorder);
            rightBorder.setActor(rightVerticalGroup);

            rightBorder.setSize(stage.getWidth() * CENTER_BORDER_WIDTH_RATIO,
                    stage.getHeight());
            rightBorder.setPosition(
                    stage.getWidth() - rightVerticalGroup.getWidth(), 0);

            actorField = new TextField("", uiSkin);
            rightVerticalGroup.addActor(actorField);
            rightVerticalGroup.setVisible(false);

            actorField.addCaptureListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    actorFieldHasFocus = true;
                    super.clicked(event, x, y);
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

            actionList = new List(uiSkin);

            actionList.setWidth(rightBorder.getWidth()
                    - rightBorder.getLineSize() * 4);
            actionList.setColor(Color.BLACK);

            final ScrollPane scroll = new ScrollPane(actionList, uiSkin);
            scroll.setFadeScrollBars(false);
            Border scrollBorder = new Border();
            scrollBorder.setLineOutsideActor(true);
            scrollBorder.setLineSize(1);
            scrollBorder.setSize(actionList.getWidth(),
                    rightBorder.getHeight() * 0.7f);
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
            HorizontalGroup buttonContainer = new HorizontalGroup();
            buttonContainer.addActor(modifyButton);
            buttonContainer.addActor(addActionButton);

            rightVerticalGroup.addActor(buttonContainer);
        }
    }

    private class LeftBorder extends Border {

        private TextButton addActorButton;
        private TextButton removeActorButton;

        private List<IGetActor> actorList;

        private TextButton setSceneBackgroundButton;

        private VerticalGroup leftVerticalGroup;

        public LeftBorder() {
            resizeLeftBorder();
            createLeftBorderContent();
        }

        private void resizeLeftBorder() {
            setSize(LEFT_BORDER_WIDTH_RATIO * stage.getWidth(),
                    LEFT_BORDER_HEIGHT_RATIO * stage.getHeight());
            setPosition(LEFT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                    LEFT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
            setLineSize(LEFT_BORDER_LINE_SIZE);
            setColor(LEFT_BORDER_COLOR);
        }

        private void createActorList() {
            actorList = new List<IGetActor>(uiSkin);

            actorList.setWidth(getWidth() - getLineSize() * 4);
            actorList.setColor(Color.BLACK);
            actorList.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int index = actorList.getSelectedIndex();
                    if (index != -1) {
                        eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                                actorList.getSelected());
                    }
                }
            });
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
            createActorList();
            final ScrollPane scroll = createScrollPane();
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
                    eventManager
                    .fireViewEvent(this, Type.REMOVE_ACTOR, activeScene);
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
                    eventManager.fireViewEvent(this, Type.NEW_ACTOR, activeScene);
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

    }
}
