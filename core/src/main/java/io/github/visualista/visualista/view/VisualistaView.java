package io.github.visualista.visualista.view;

import io.github.visualista.visualista.controller.EditorViewEvent.Type;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;

import io.github.visualista.visualista.controller.IVisualistaView;
import io.github.visualista.visualista.controller.ViewEventListener;
import io.github.visualista.visualista.controller.ViewEventManager;
import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.model.Grid;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.util.BiDiMap;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.IObjectCreator;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

public class VisualistaView implements ApplicationListener, IVisualistaView,
        FilePickerListener, TabClickListener {
    private static final float SIDE_BORDERS_WIDTH_EDITOR_RATIO = 2.0f / 9;
    private static final float SIDE_BORDERS_WIDTH_PLAYER_RATIO = 1.5f / 9;

    private Stage stage;

    private Skin uiSkin;
    private ImageButton cursorButton;
    private ImageButton handButton;
    private ImageButton arrowButton;
    private ImageButton actorImage;

    private TextButton newActorButton;
    private TextButton actorsButton;
    private TextButton dialogsButton;
    private TextButton newSceneButton;
    private TextButton modifyButton;
    private TextButton addActionButton;
    private TextButton addSceneBackgroundButton;

    private Label actorLabel;
    private Label actionLabel;

    private Matrix<Image> gridButtons;

    private List actorList;
    private List actionList;

    private Border leftBorder;
    private Border rightBorder;
    private Border upperBorder;
    private Border lowerBorder;
    private Border centerBorder;

    private VerticalGroup leftVerticalGroup;
    private VerticalGroup rightVerticalGroup;
    private VerticalGroup centerVerticalGroup;

    private HorizontalGroup sceneButtonGroup;

    private final ViewEventManager eventManager = new ViewEventManager();

    private Dimension configDimension;

    private ArrayList<Tab> overflowingTabs;
    private BiDiMap<Tab, IGetScene> tabs;

    private Actor overflowDropdownButton;
    private List contextMenu;
    private ScrollPane contextMenuScroll;

    private HorizontalGroup tabExtraButtons;

    private Border cursorButtonBorder;

    private Border handButtonBorder;

    private Border arrowButtonBorder;

    private boolean isReady = false;

    private ImageButton openButton;

    private Border openButtonBorder;

    private IFilePicker filePicker;

    private IGetScene activeScene;

    private TextArea textInput;

    private Image sceneBackgroundImage;

    private Border centerVerticalGroupBorder;

    private TextField actorField;

    private String renameActor;

    private java.util.List<IGetActor> actorNameList;
    private Tab editingTab;

    protected IGetActor selectedActor;

    protected boolean actorFieldHasFocus;

    private ImageButton saveButton;

    private Border saveButtonBorder;

    public VisualistaView(Dimension dimension, IFilePicker filePicker) {
        this.configDimension = dimension;
        tabs = new BiDiMap<Tab, IGetScene>();
        this.filePicker = filePicker;
    }

    @Override
    public final void create() {
        //TODO: A non-state way of deciding if we are playing or editing the Visual Novel
        createEditorView();
    }
    
    
    // Start Create Editor //
    
    private void createEditorView(){
        stage = new Stage();
        stage.getViewport().setWorldHeight(configDimension.getHeight());
        stage.getViewport().setWorldWidth(configDimension.getWidth());

        stage.clear();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        createLeftEditorBorderContent();
        createRightEditorBorderContent();
        creatLowerEditorBorderContent();
        createCenterEditorBorderContent();
        createUpperEditorBorderContent();
        resizeCenterBorder();

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(false);
        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
        
    }
    
    private void createUpperEditorBorderContent() {
        overflowingTabs = new ArrayList<Tab>();
        newSceneButton = new TextButton("+", uiSkin);
        overflowDropdownButton = new TextButton(">", uiSkin);
        tabExtraButtons = new HorizontalGroup();
        tabExtraButtons.addActor(newSceneButton);
        tabExtraButtons.addActor(overflowDropdownButton);
        overflowDropdownButton.setVisible(false);
        overflowDropdownButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                contextMenuScroll.setVisible(true);
            }
        });
        newSceneButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                eventManager.fireViewEvent(this, Type.NEW_SCENE);
            }
        });

        upperBorder = new Border();
        upperBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                stage.getHeight() * (1 / 25f));
        upperBorder.setPosition(rightSideOf(leftBorder), stage.getHeight()
                - upperBorder.getHeight());
        sceneButtonGroup = new HorizontalGroup();
        sceneButtonGroup.addActor(tabExtraButtons);

        upperBorder.setActor(sceneButtonGroup);
        sceneButtonGroup.setX(3);
        stage.addActor(upperBorder);

        final String[] actors = {};
        contextMenu = new List(uiSkin);
        contextMenu.setWidth(150);
        contextMenu.setColor(Color.BLACK);
        contextMenuScroll = new ScrollPane(contextMenu, uiSkin);
        contextMenuScroll.setFadeScrollBars(false);
        contextMenuScroll.setWidth(actionList.getWidth());
        contextMenuScroll.setPosition(
                rightBorder.getX() - contextMenuScroll.getWidth(),
                upperBorder.getY() - contextMenuScroll.getHeight());

        contextMenu.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                Gdx.app.log("contextMenu", "clicked");
            }
        });
        contextMenuScroll.setVisible(false);
        stage.addActor(contextMenuScroll);
        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {
                Actor hitObject = stage.hit(x, y, true);
                boolean contextMenuClicked = hitObject == contextMenuScroll
                        || hitObject == contextMenu;
                if (!contextMenuClicked) {
                    contextMenuScroll.setVisible(false);
                }
                if (editingTab != null
                        && (hitObject != editingTab && hitObject != editingTab
                                .getTextField())) {
                    String newName = editingTab.newName();
                    if (editingTab.nameWasChanged()) {
                        eventManager.fireViewEvent(this,
                                Type.CHANGE_SCENE_NAME,
                                tabs.getValue(editingTab), newName);
                    } else {
                        editingTab.stopEditing();

                    }
                    editingTab = null;
                }
                Gdx.app.log("", ""+actorFieldHasFocus+" "+selectedActor);
                if (actorFieldHasFocus && selectedActor != null
                        && hitObject != actorField) {

                    actorFieldHasFocus = false;
                    String renameActorTo = actorField.getText();

                    eventManager.fireViewEvent(this, Type.CHANGE_ACTOR_NAME,
                            actorNameList.get(actorList.getSelectedIndex()),
                            renameActorTo);
                }
                return false;
            }

        });
    }

    private void creatLowerEditorBorderContent() {
        lowerBorder = new Border();
        lowerBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                100);
        lowerBorder.setPosition(rightSideOf(leftBorder), 0);
        textInput = new TextArea("", uiSkin);
        lowerBorder.setActor(textInput);
        stage.addActor(lowerBorder);
    }

    private void createRightEditorBorderContent() {

        rightVerticalGroup = new VerticalGroup();
        rightBorder = new Border();
        stage.addActor(rightBorder);
        rightBorder.setActor(rightVerticalGroup);

        rightBorder.setSize(stage.getWidth() * SIDE_BORDERS_WIDTH_EDITOR_RATIO,
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

        final Drawable actorDraw = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        actorImage = new ImageButton(actorDraw);
        actorImage.setSize(70, 70);
        rightVerticalGroup.addActor(actorImage);

        actionLabel = new Label("Actions", uiSkin);
        actionLabel.setSize(50, 50);
        actionLabel.setColor(Color.BLACK);
        rightVerticalGroup.addActor(actionLabel);

        actionList = new List(uiSkin);

        actionList.setWidth(rightBorder.getWidth() - rightBorder.getLineSize()
                * 4);
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

    private void createCenterEditorBorderContent() {
        centerVerticalGroup = new VerticalGroup();
        centerBorder = new Border();
        stage.addActor(centerBorder);

        sceneBackgroundImage = new Image();
        Stack stack = new Stack();
        stack.add(sceneBackgroundImage);

        centerVerticalGroupBorder = new Border();
        centerVerticalGroupBorder.setActor(centerVerticalGroup);
        stack.add(centerVerticalGroupBorder);

        centerBorder.setActor(stack);
        gridButtons = new Matrix<Image>(new Dimension(5, 5));
    }

    private void createLeftEditorBorderContent() {
        leftVerticalGroup = new VerticalGroup();
        leftBorder = new Border();
        stage.addActor(leftBorder);

        leftBorder.setActor(leftVerticalGroup);
        leftBorder.setSize(stage.getWidth() * SIDE_BORDERS_WIDTH_EDITOR_RATIO,
                stage.getHeight());
        leftBorder.setPosition(0, 0);

        final Drawable open = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/open.png"))));
        openButton = new ImageButton(open);
        openButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                openNovel();
                super.clicked(event, x, y);
            }

        });
        openButtonBorder = new Border();
        openButtonBorder.setLineSize(0);
        openButtonBorder.setActor(openButton);
        
        final Drawable save = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/save.png"))));
        saveButton = new ImageButton(save);
        saveButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveNovel();
                super.clicked(event, x, y);
            }

        });
        saveButtonBorder = new Border();
        saveButtonBorder.setLineSize(0);
        saveButtonBorder.setActor(saveButton);

        final Drawable cursor = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        cursorButton = new ImageButton(cursor);
        cursorButton.addListener(new ClickListener());
        cursorButtonBorder = new Border();
        cursorButtonBorder.setLineSize(0);
        cursorButtonBorder.setActor(cursorButton);

        final Drawable hand = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/hand.png"))));

        handButton = new ImageButton(hand);
        handButton.addListener(new ClickListener());
        handButtonBorder = new Border();
        handButtonBorder.setLineSize(0);
        handButtonBorder.setActor(handButton);

        final Drawable arrow = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/arrow.png"))));

        arrowButton = new ImageButton(arrow);
        arrowButton.addListener(new ClickListener());
        arrowButtonBorder = new Border();
        arrowButtonBorder.setLineSize(0);
        arrowButtonBorder.setActor(arrowButton);

        HorizontalGroup buttonContainer1 = new HorizontalGroup();

        openButtonBorder.setSize(50, 40);
        saveButtonBorder.setSize(50,40);
        cursorButtonBorder.setSize(50, 40);
        handButtonBorder.setSize(50, 40);
        arrowButtonBorder.setSize(50, 40);

        buttonContainer1.addActor(saveButtonBorder);
        buttonContainer1.addActor(openButtonBorder);
        buttonContainer1.addActor(cursorButtonBorder);
        buttonContainer1.addActor(handButtonBorder);
        buttonContainer1.addActor(arrowButtonBorder);
        leftVerticalGroup.addActor(buttonContainer1);

        actorList = new List(uiSkin);

        actorList
                .setWidth(leftBorder.getWidth() - leftBorder.getLineSize() * 4);
        actorList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actorList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setSize(actorList.getWidth(),
                leftBorder.getHeight() * 0.7f);
        scrollBorder.setActor(scroll);
        leftVerticalGroup.addActor(scrollBorder);
        actorList.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int index = actorList.getSelectedIndex();
                if (index != -1) {
                    eventManager.fireViewEvent(this, Type.SELECT_ACTOR,
                            actorNameList.get(index));
                }
            }

        });

        HorizontalGroup buttonContainer2 = new HorizontalGroup();

        actorsButton = new TextButton("Add actor", uiSkin);
        actorsButton.setSize(150, 20);
        buttonContainer2.addActor(actorsButton);

        actorsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eventManager.fireViewEvent(this, Type.NEW_ACTOR, activeScene);
            }
        });

        leftVerticalGroup.addActor(buttonContainer2);

        addSceneBackgroundButton = new TextButton("Set background", uiSkin);
        leftVerticalGroup.addActor(addSceneBackgroundButton);

        addSceneBackgroundButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO selected scene and image
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Chose image", "png");
                filePicker.openFileDialog(new FilePickerListener() {

                    @Override
                    public void fileOpened(File selectedFile) {
                        eventManager.fireViewEvent(this,
                                Type.CHANGE_SCENE_IMAGE, activeScene,
                                selectedFile);
                    }

                    @Override
                    public void fileSaved(File selectedFile) {
                    }
                }, filter);

                super.clicked(event, x, y);
            }

        });
    }

    protected void saveNovel() {
        filePicker.saveFileDialog(this,
                new FileNameExtensionFilter("Visualista Novel", "vis"));
    }

    // End Create Editor //
    
    // Start Create Player //

    private void createPlayerView(){
        stage = new Stage();
        stage.getViewport().setWorldHeight( configDimension.getHeight() );
        stage.getViewport().setWorldWidth( configDimension.getWidth() );
        stage.clear();
        
        
        uiSkin = new Skin( Gdx.files.internal("uiskin.json"));
        
        createLeftPlayerBorderContent();
        createRightPlayerBorderContent();
        createCenterPlayerBorderContent();
        
        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(false);
        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
    }
    
    private void createCenterPlayerBorderContent(){
        
        // Defining the necessary variables //
        centerVerticalGroup = new VerticalGroup();
        centerBorder = new Border();
        sceneBackgroundImage = new Image();
        centerVerticalGroupBorder = new Border();
        gridButtons = new Matrix<Image>( new Dimension( 5, 5 ) );
        // End defining variables //
        
        // Declaring local variables //
        Stack stack = new Stack();
        // End declaring local variables //
        
        stage.addActor(centerBorder);
        centerVerticalGroupBorder.setActor(centerVerticalGroup);
        
        // Adding actors in the right order to the stack //
        stack.add(sceneBackgroundImage);
        stack.add(centerVerticalGroupBorder);
       // End adding actors to stack //
        
        centerBorder.setActor(stack);
        
        
    }

    private void createRightPlayerBorderContent(){
        // Defining the necessary variables //
        rightVerticalGroup = new VerticalGroup();
        rightBorder = new Border();
        // End defining variables //
        
        // Declaring local variables //
        
        // End declaring local variables //
        
        // Reference actor to stage //
        stage.addActor(rightBorder);
        rightBorder.setActor(rightVerticalGroup);
        // End referencing //
        
        // Define appearance of right border //
        rightBorder.setSize( stage.getWidth() * SIDE_BORDERS_WIDTH_PLAYER_RATIO, stage.getHeight() );
        rightBorder.setPosition( stage.getWidth() - rightVerticalGroup.getWidth(), 0 );
        rightBorder.setColor(Color.BLACK);
        // End defining appearance //
        
    }
    
    private void createLeftPlayerBorderContent(){
        // Defining the necessary variables //
        leftVerticalGroup = new VerticalGroup();
        leftBorder = new Border();
        // End defining variables //
        
        // Declare local variables //
        
        // End declare local variables //
        
        // Reference actor to stage //
        stage.addActor(leftBorder);
        leftBorder.setActor(leftVerticalGroup);
        // End referencing //
        
        // Define appearance of left border //¨
        leftBorder.setSize( stage.getWidth() * SIDE_BORDERS_WIDTH_PLAYER_RATIO, stage.getHeight() );
        leftBorder.setPosition(0, 0);
        leftBorder.setColor(Color.BLACK);
        // End defining appearance //
        
    }
    // End Create Player //
    
    
    private void fillGridFromScene(IGetScene scene) {
        gridButtons = new Matrix<Image>(scene.getGrid().getSize());
        final Drawable tile = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/transparent.png"))));

        for (int i = 0; i < scene.getGrid().getSize().getHeight(); ++i) {
            for (int j = 0; j < scene.getGrid().getSize().getWidth(); ++j) {
                gridButtons.setAt(new Point(i, j), new Image(tile));
            }
        }
        fillGrid(centerVerticalGroup, gridButtons);
    }

    private void resizeCenterBorder() {
        centerBorder.setSize(
                horizontalDistanceBetween(leftBorder, rightBorder),
                (upperBorder.getY() - lowerBorder.getY() - lowerBorder
                        .getHeight()));
        centerBorder.setPosition(rightSideOf(leftBorder), lowerBorder.getY()
                + lowerBorder.getHeight());
        centerVerticalGroupBorder.setLineSize(0);
        centerVerticalGroupBorder.setSize(centerBorder.getWidth(),
                centerBorder.getHeight());

    }

       protected void openNovel() {
        filePicker.openFileDialog(VisualistaView.this,
                new FileNameExtensionFilter("Visualista Novel", "vis"));
    }

    @Override
    public boolean getIsReady() {
        return isReady;
    }

    private float rightSideOf(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return actor.getX() + actor.getWidth();
    }

    public final void fillGrid(VerticalGroup group, IMatrixGet<Image> data) {
        float prefferedButtonHeight = group.getHeight()
                / data.getSize().getHeight();
        float prefferedButtonWidth = group.getWidth()
                / data.getSize().getWidth();
        float buttonLength = Math.min(prefferedButtonWidth,
                prefferedButtonHeight);
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
        for (Actor overflowedTab : overflowingTabs) {
            sceneButtonGroup.addActorBefore(firstChild, overflowedTab);
        }
        overflowingTabs.clear();

        for (com.badlogic.gdx.scenes.scene2d.Actor child : children) {
            totalWidth += child.getWidth();
        }
        while (totalWidth > sceneButtonGroup.getWidth()
                && sceneButtonGroup.getChildren().size > 1) {
            Actor currentlyFirstChild = sceneButtonGroup.getChildren().get(0);
            totalWidth -= currentlyFirstChild.getWidth();
            sceneButtonGroup.removeActor(currentlyFirstChild);
            overflowDropdownButton.setVisible(true);
            overflowingTabs.add((Tab) currentlyFirstChild);
        }
        contextMenu.setVisible(true);
        ArrayList<String> objects = new ArrayList<String>(
                overflowingTabs.size());
        for (Tab removedTab : overflowingTabs) {
            objects.add(removedTab.getText().toString());
        }
        contextMenu.setItems(objects.toArray());
    }

    @Override
    public void updateScene(IGetScene scene) {
        String name = scene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
            name += " ";
        }

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
        if (oldTab != null) {
            tabs.removeByKey(oldTab);
            overflowingTabs.remove(oldTab);
            sceneButtonGroup.addActorBefore(oldTab, tab);
            sceneButtonGroup.removeActor(oldTab);
        } else {
            sceneButtonGroup.addActorBefore(tabExtraButtons, tab);
        }
        tabs.put(tab, scene);
        hideOverFlowingScenes();

        String[] actorNames = new String[scene.getActorsInScene().size()];
        for (int i = 0; i < scene.getActorsInScene().size(); i++) {
            Gdx.app.log("actorName", ""+scene.getActorsInScene().get(i).getName());
            actorNames[i] = scene.getActorsInScene().get(i).getName();
        }
        actorNameList = scene.getActorsInScene();
        actorList.setItems(actorNames);

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
            tabs.removeByKey(oldTab);
            overflowingTabs.remove(oldTab);
            sceneButtonGroup.addActorAt(0, tab);
            sceneButtonGroup.removeActor(oldTab);
        } else {
            sceneButtonGroup.addActorAt(0, tab);
        }
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
        actionList.setItems(new String[] {});
    }

    private void clearActorList() {
        actorList.setItems(new String[] {});

    }

    private void clearSceneTabs() {
        overflowingTabs.clear();
        sceneButtonGroup.clearChildren();
        sceneButtonGroup.addActor(tabExtraButtons);
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
    public void selectActor(IGetActor targetObject) {
        selectedActor = targetObject;
        if (targetObject != null) {
            rightVerticalGroup.setVisible(true);
            actorField.setText(targetObject.getName());
        } else {
            rightVerticalGroup.setVisible(false);
        }

    }

    @Override
    public void updateActor(IGetActor updatedActor) {
        // TODO Make better
        if(selectedActor==updatedActor){
            changeActiveScene(this.activeScene);
        }
    }
}
