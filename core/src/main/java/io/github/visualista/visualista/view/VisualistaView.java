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
import io.github.visualista.visualista.model.Grid;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.IObjectCreator;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.util.*;

public class VisualistaView implements ApplicationListener, IVisualistaView {
    private static final float LEFT_BORDER_WIDTH_RATIO = 2.0f / 9;

    private Stage stage;

    private Skin uiSkin;
    private ImageButton leftButton;
    private ImageButton centerButton;
    private ImageButton rightButton;
    private ImageButton actorImage;

    private TextButton newActorButton;
    private TextButton actorsButton;
    private TextButton dialogsButton;
    private TextButton newSceneButton;
    private TextButton modifyButton;
    private TextButton addActionButton;

    private Label actorLabel;
    private Label actionLabel;

    private ArrayList<TextButton> sceneButtons;
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

    private boolean tabsOverflowing;

    private ArrayList<Actor> overflowingTabs;

    private Actor overflowDropdownButton;

    private HorizontalGroup tabExtraButtons;


    public VisualistaView(Dimension dimension) {
        this.configDimension = dimension;
    }

    @Override
    public final void create() {
        stage = new Stage(configDimension.getWidth(),
                configDimension.getHeight(), false);
        stage.clear();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        gridButtons = new Matrix<Image>(new Dimension(5, 5));
        final Drawable tile = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/tile.png"))));

        for (int i = 0; i < 25; i++) {
            gridButtons.fillWith(new IObjectCreator<Image>() {

                @Override
                public Image createObject() {
                    return new Image(tile);
                }

            });
        }

        overflowingTabs = new ArrayList<Actor>();
        newSceneButton = new TextButton("+", uiSkin);
        overflowDropdownButton = new TextButton(">",uiSkin);
        tabExtraButtons = new HorizontalGroup();
        tabExtraButtons.addActor(newSceneButton);
        tabExtraButtons.addActor(overflowDropdownButton);
        overflowDropdownButton.setVisible(false);

        newSceneButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                eventManager.fireViewEvent(this, Type.NEW_SCENE);
            }
        });

        sceneButtons = new ArrayList<TextButton>();

        createLeftBorderContent();
        createRightBorderContent();
        upperBorder = new Border();
        upperBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                200);
        upperBorder.setPosition(rightSideOf(leftBorder), stage.getHeight()
                - upperBorder.getHeight());
        sceneButtonGroup = new HorizontalGroup();
        sceneButtonGroup.addActor(tabExtraButtons);

        upperBorder.setActor(sceneButtonGroup);
        stage.addActor(upperBorder);
        lowerBorder = new Border();
        lowerBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                200);
        lowerBorder.setPosition(rightSideOf(leftBorder), 0);
        stage.addActor(lowerBorder);
        createCenterBorderContent();

        this.addScene(new IGetScene() {

            @Override
            public String getStoryText() {
                return "No story";
            }

            @Override
            public String getName() {
                return "Dummy scene";
            }

            @Override
            public java.util.List<IGetActor> getActorsInScene() {
                return null;
            }

            @Override
            public Grid getGrid() {
                return null;
            }

        });

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
    }

    private float rightSideOf(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return actor.getX() + actor.getWidth();
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

        final String[] actors = { "Jacob", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej" };

        actionList = new List(actors, uiSkin);

        actionList.setWidth(rightBorder.getWidth() - rightBorder.getLineSize()
                * 4);
        actionList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actionList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setLineOutsideActor(true);
        scrollBorder.setLineSize(1);
        scrollBorder.setSize(actionList.getWidth(), 400);
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

        final Drawable cursor = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        leftButton = new ImageButton(cursor);
        leftButton.setPosition(60, 750);
        leftButton.setSize(50, 50);
        leftButton.addListener(new ClickListener());

        final Drawable hand = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/hand.png"))));

        centerButton = new ImageButton(hand);
        centerButton.setSize(50, 50);
        centerButton.addListener(new ClickListener());

        final Drawable arrow = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/arrow.png"))));

        rightButton = new ImageButton(arrow);
        rightButton.setSize(50, 50);
        rightButton.addListener(new ClickListener());

        HorizontalGroup buttonContainer1 = new HorizontalGroup();

        buttonContainer1.addActor(leftButton);
        buttonContainer1.addActor(centerButton);
        buttonContainer1.addActor(rightButton);
        leftVerticalGroup.addActor(buttonContainer1);

        final String[] actors = { "Jacob", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej",
                "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej", "Hej" };

        actorList = new List(actors, uiSkin);

        actorList.setSize(leftBorder.getWidth(), 400);
        actorList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actorList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setSize(leftBorder.getWidth(), 400);
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
        String name = newScene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
            name += " ";
        }
        TextButton tab = new TextButton(name, uiSkin);
        sceneButtons.add(tab);
        sceneButtonGroup.addActorBefore(tabExtraButtons, tab);
        hideOverFlowingScenes();

    }

    private void hideOverFlowingScenes() {
        SnapshotArray<Actor> children = sceneButtonGroup.getChildren();
        overflowDropdownButton.setVisible(false);
        float totalWidth = 0;
        Actor firstChild = null;
        if(children.size>0){
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
            overflowingTabs.add(currentlyFirstChild);
        }
    }

    @Override
    public void updateScene(IGetScene currentScene) {
        // TODO Auto-generated method stub

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
}
