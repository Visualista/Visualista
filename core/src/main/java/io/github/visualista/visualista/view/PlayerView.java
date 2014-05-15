package io.github.visualista.visualista.view;

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

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.IVisualistaView;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
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

public class PlayerView implements ApplicationListener, IVisualistaView,
        FilePickerListener, TabClickListener {
    
    //Defining static objects and variables //
    private static final float SIDE_BORDER_RATIO = 1.5f / 9;
    private static final Skin BASE_SKIN = new Skin( Gdx.files.internal( "uiskin.json" ) );
    private static final Drawable EMPTY_ICON = new TextureRegionDrawable(
            new TextureRegion( 
                    new Texture( Gdx.files.internal( "icons/transparent.png" ) ) ) );
    // End static objects and variables //

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

    public PlayerView(Dimension dimension, IFilePicker filePicker) {
        this.configDimension = dimension;
        tabs = new BiDiMap<Tab, IGetScene>();
        this.filePicker = filePicker;
    }

    @Override
    public final void create() {
        createPlayerView();
    }
    
    
  
    // Start Create Player //

    private void createPlayerView(){
        stage = new Stage();
        stage.getViewport().setWorldHeight( configDimension.getHeight() );
        stage.getViewport().setWorldWidth( configDimension.getWidth() );
        stage.clear();
        
        
        uiSkin = BASE_SKIN;
        
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
        rightBorder.setSize( stage.getWidth() * SIDE_BORDER_RATIO, stage.getHeight() );
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
        leftBorder.setSize( stage.getWidth() * SIDE_BORDER_RATIO, stage.getHeight() );
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
        filePicker.openFileDialog(PlayerView.this,
                new FileNameExtensionFilter("Visualista Novel (.vis)", "vis"));
    }

    @Override
    public boolean getIsReady() {
        return isReady;
    }

    private float rightSideOf(com.badlogic.gdx.scenes.scene2d.Actor gdxActor) {
        return gdxActor.getX() + gdxActor.getWidth();
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
