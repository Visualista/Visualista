package io.github.visualista.visualista.view;

import io.github.visualista.visualista.controller.EditorViewEvent.Type;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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

import io.github.visualista.visualista.controller.ViewEventListener;
import io.github.visualista.visualista.controller.ViewEventManager;
import io.github.visualista.visualista.model.Actor;
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

    private final ViewEventManager eventManager = new ViewEventManager();

    private Dimension configDimension;

    // private Table leftTable;

    // private TextureAtlas atlas;

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

        newSceneButton = new TextButton("+", uiSkin);

        newSceneButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                eventManager.fireViewEvent(this, Type.NEW_SCENE);
            }
        });

        sceneButtons = new ArrayList<TextButton>();
        sceneButtons.add(new TextButton("Scene1", uiSkin));

        placeSceneButtons();
        createLeftBorderContent();
        createRightBorderContent();
        upperBorder = new Border();
        upperBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                200);
        upperBorder.setPosition(leftBorder.getX() + leftBorder.getWidth(),
                stage.getHeight() - upperBorder.getHeight());
        stage.addActor(upperBorder);
        lowerBorder = new Border();
        lowerBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                200);
        Gdx.app.log("Width", lowerBorder.getWidth() + "");
        lowerBorder.setPosition(leftBorder.getX() + leftBorder.getWidth(), 0);
        stage.addActor(lowerBorder);
        createCenterBorderContent();

        // Use if you need to zoom out a bit
        //((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
    }

    private void createRightBorderContent() {

        rightVerticalGroup = new VerticalGroup();
        rightBorder = new Border();
        stage.addActor(rightBorder);
        rightBorder.setActor(rightVerticalGroup);

        rightBorder.setSize(stage.getWidth() * LEFT_BORDER_WIDTH_RATIO,
                stage.getHeight());
        rightBorder.setPosition(
                stage.getWidth() - rightVerticalGroup.getWidth(), 3);

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

        Actor a1 = new Actor();
        a1.setName("Jacob");
        Actor a2 = new Actor();
        a2.setName("Ja");
        final String[] actors = { a1.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName() };

        actionList = new List(actors, uiSkin);

        actionList.setSize(350, 400);
        actionList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actionList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setSize(400, 400);
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
        centerBorder.setSize(rightBorder.getX() - leftBorder.getX()
                - leftBorder.getWidth(),
                (upperBorder.getY() - lowerBorder.getY() - lowerBorder
                        .getHeight()));
        Gdx.app.log("Height", "" + ((stage.getHeight() - upperBorder.getY())));
        centerBorder.setPosition(leftBorder.getWidth(), lowerBorder.getY()
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
        leftBorder.setPosition(3, 3);

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

        Actor a1 = new Actor();
        a1.setName("Jacob");
        Actor a2 = new Actor();
        a2.setName("Ja");
        final String[] actors = { a1.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName() };

        actorList = new List(actors, uiSkin);

        actorList.setSize(leftBorder.getWidth(), 400);
        actorList.setColor(Color.BLACK);

        final ScrollPane scroll = new ScrollPane(actorList, uiSkin);
        scroll.setFadeScrollBars(false);
        Border scrollBorder = new Border();
        scrollBorder.setSize(400, 400);
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
                row.addActor(border);
            }
        }
    }

    public final void placeSceneButtons() {
        int x = 452;
        for (int i = 0; i < sceneButtons.size(); i++) {
            sceneButtons.get(i).setSize(70, 20);
            sceneButtons.get(i).setPosition(x, 701);
            x = x + 70;

            stage.addActor(sceneButtons.get(i));
        }
        newSceneButton.setSize(20, 20);
        newSceneButton.setPosition(452 + 70 * sceneButtons.size() + 1, 701);
        stage.addActor(newSceneButton);
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

        sceneButtons.add(new TextButton(newScene.getName(), uiSkin));
        placeSceneButtons();

    }

    @Override
    public void updateScene(IGetScene currentScene) {
        // TODO Auto-generated method stub

    }

    private static float horizontalDistanceBetween(
            com.badlogic.gdx.scenes.scene2d.Actor actor1,
            com.badlogic.gdx.scenes.scene2d.Actor actor2) {
        float distance = actor1.getX() + actor1.getWidth() - actor2.getX();
        if (distance >= 0)
            return distance;
        {
        }
        distance = actor2.getX() + actor2.getWidth() - actor1.getX();
        if (distance >= 0) {
            return distance;
        } else {
            return 0;
        }
    }
}
