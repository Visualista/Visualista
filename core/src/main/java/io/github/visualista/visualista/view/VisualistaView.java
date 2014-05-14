package io.github.visualista.visualista.view;

import io.github.visualista.visualista.controller.EditorViewEvent.Type;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;

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
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;

public class VisualistaView implements ApplicationListener, IVisualistaView,
        FilePickerListener {
    private static final float LEFT_BORDER_WIDTH_RATIO = 2.0f / 9;

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

    public VisualistaView(Dimension dimension, IFilePicker filePicker) {
        this.configDimension = dimension;
        tabs = new BiDiMap<Tab, IGetScene>();
        this.filePicker = filePicker;
    }

    @Override
    public final void create() {
        stage = new Stage(configDimension.getWidth(),
                configDimension.getHeight(), false);
        stage.clear();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        createLeftBorderContent();
        createRightBorderContent();
        createLowerBorderContent();
        createCenterBorderContent();
        createUpperBorderContent();
        resizeCenterBorder();

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(false);
        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
    }

    private void createUpperBorderContent() {
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
        contextMenu = new List(actors, uiSkin);
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
                return contextMenuClicked;
            }

        });
    }

    private void createLowerBorderContent() {
        lowerBorder = new Border();
        lowerBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                100);
        lowerBorder.setPosition(rightSideOf(leftBorder), 0);
        stage.addActor(lowerBorder);
    }

    private void createRightBorderContent() {

        rightVerticalGroup = new VerticalGroup();
        rightBorder = new Border();
        stage.addActor(rightBorder);
        rightBorder.setActor(rightVerticalGroup);

        rightBorder.setSize(stage.getWidth() * LEFT_BORDER_WIDTH_RATIO,
                stage.getHeight());
        rightBorder.setPosition(
                stage.getWidth() - rightVerticalGroup.getWidth(), 0);

        actorLabel = new Label("Hej", uiSkin);
        actorLabel.setSize(50, 50);
        actorLabel.setColor(Color.BLACK);
        rightVerticalGroup.addActor(actorLabel);

        final Drawable actorDraw = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        actorImage = new ImageButton(actorDraw);
        actorImage.setSize(70, 70);
        rightVerticalGroup.addActor(actorImage);

        actionLabel = new Label("Actions", uiSkin);
        actionLabel.setSize(50, 50);
        actionLabel.setColor(Color.BLACK);
        rightVerticalGroup.addActor(actionLabel);

        actionList = new List(new String[0], uiSkin);

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

    private void createCenterBorderContent() {
        centerVerticalGroup = new VerticalGroup();
        centerBorder = new Border();
        stage.addActor(centerBorder);

        centerBorder.setActor(centerVerticalGroup);
        gridButtons = new Matrix<Image>(new Dimension(5, 5));
        final Drawable tile = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/transparent.png"))));

        for (int i = 0; i < 25; i++) {
            gridButtons.fillWith(new IObjectCreator<Image>() {

                @Override
                public Image createObject() {
                    return new Image(tile);
                }

            });
        }

    }

    private void resizeCenterBorder() {
        centerBorder.setSize(
                horizontalDistanceBetween(leftBorder, rightBorder),
                (upperBorder.getY() - lowerBorder.getY() - lowerBorder
                        .getHeight()));
        centerBorder.setPosition(rightSideOf(leftBorder), lowerBorder.getY()
                + lowerBorder.getHeight());

        fillGrid(centerVerticalGroup, gridButtons);
    }

    private void createLeftBorderContent() {
        leftVerticalGroup = new VerticalGroup();
        leftBorder = new Border();
        stage.addActor(leftBorder);

        leftBorder.setActor(leftVerticalGroup);
        leftBorder.setSize(stage.getWidth() * LEFT_BORDER_WIDTH_RATIO,
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
        cursorButtonBorder.setSize(50, 40);
        handButtonBorder.setSize(50, 40);
        arrowButtonBorder.setSize(50, 40);

        buttonContainer1.addActor(openButtonBorder);
        buttonContainer1.addActor(cursorButtonBorder);
        buttonContainer1.addActor(handButtonBorder);
        buttonContainer1.addActor(arrowButtonBorder);
        leftVerticalGroup.addActor(buttonContainer1);

        actorList = new List(new String[0], uiSkin);

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

        actorList.addCaptureListener(new EventListener() {

            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                }
                return false;
            }

        });

        HorizontalGroup buttonContainer2 = new HorizontalGroup();

        newActorButton = new TextButton("New", uiSkin);
        newActorButton.setSize(100, 20);
        buttonContainer2.addActor(newActorButton);

        actorsButton = new TextButton("Actors", uiSkin);
        actorsButton.setSize(150, 20);
        buttonContainer2.addActor(actorsButton);

        dialogsButton = new TextButton("Dialogs", uiSkin);
        dialogsButton.setSize(150, 20);
        buttonContainer2.addActor(dialogsButton);
        leftVerticalGroup.addActor(buttonContainer2);

        addSceneBackgroundButton = new TextButton("Add background", uiSkin);
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
                                Type.CHANGE_SCENE_IMAGE, null, selectedFile);
                    }

                    @Override
                    public void fileSaved(File selectedFile) {
                    }
                }, filter);

                super.clicked(event, x, y);
            }

        });
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
        stage.setViewport(configDimension.getWidth(),
                configDimension.getHeight(), true);
        stage.getCamera().translate(-stage.getGutterWidth(),
                -stage.getGutterHeight(), 0);

    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 2);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
    public void updateScene(IGetScene currentScene) {
        String name = currentScene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
            name += " ";
        }
        Tab tab = new Tab(name, uiSkin);
        tab.setHeight(upperBorder.getHeight());
        Tab oldTab = tabs.getKey(currentScene);
        if (oldTab != null) {
            tabs.removeByKey(oldTab);
            overflowingTabs.remove(oldTab);
            sceneButtonGroup.addActorBefore(oldTab, tab);
            sceneButtonGroup.removeActor(oldTab);
        } else {
            sceneButtonGroup.addActorBefore(tabExtraButtons, tab);
        }
        tabs.put(tab, currentScene);
        hideOverFlowingScenes();
        String[] actorNames = new String[currentScene.getActorsInScene().size()];
        for (int i = 0; i < currentScene.getActorsInScene().size(); i++) {
            actorNames[i] = currentScene.getActorsInScene().get(i).getName();
        }
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
    public void changeActiveScene(Scene scene) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeActiveNovel(IGetNovel updatedNovel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fileOpened(File selectedFile) {
        Gdx.app.log("", "");
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
        actionList.clear();
    }

    private void clearActorList() {
        actorList.clear();

    }

    private void clearSceneTabs() {
        overflowingTabs.clear();
        sceneButtonGroup.clearChildren();
        sceneButtonGroup.addActor(tabExtraButtons);
        tabs.clear();
    }
}
