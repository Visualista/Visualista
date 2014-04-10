package io.github.visualista.view;

import io.github.visualista.visualista.core.model.Actor;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

public class VisualistaView implements ApplicationListener {
    private Stage stage;

    private Skin uiSkin;

    private ImageButton leftButton;
    private ImageButton centerButton;
    private ImageButton rightButton;
    private TextButton newActorButton;
    private TextButton actorsButton;
    private TextButton dialogsButton;
    private TextButton newSceneButton;

    private ArrayList<TextButton> sceneButtons;
    private ArrayList<ImageButton> gridButtons;

    private Border leftBorder;
    private Border rightBorder;
    private Border upperBorder;
    private Border lowerBorder;

    // private TextureAtlas atlas;

    @Override
    public void create() {
        stage = new Stage(1800, 900, true);
        stage.clear();

        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        gridButtons = new ArrayList<ImageButton>();

        Drawable tile = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/hand.png"))));
        for (int i = 0; i < 25; i++) {
            gridButtons.add(new ImageButton(tile));
        }

        newSceneButton = new TextButton("+", uiSkin);

        sceneButtons = new ArrayList<TextButton>();
        sceneButtons.add(new TextButton("Scene1", uiSkin));

        leftBorder = new Border(null);
        leftBorder.setSize(450, 1800);
        leftBorder.setPosition(0, 0);
        stage.addActor(leftBorder);

        rightBorder = new Border(null);
        rightBorder.setSize(450, 1800);
        rightBorder.setPosition(1350, 0);
        stage.addActor(rightBorder);

        upperBorder = new Border(null);
        upperBorder.setSize(900, 200);
        upperBorder.setPosition(450, 700);
        stage.addActor(upperBorder);

        lowerBorder = new Border(null);
        lowerBorder.setSize(900, 200);
        lowerBorder.setPosition(450, 0);
        stage.addActor(lowerBorder);

        Label label = new Label("Hej", uiSkin);
        label.setX(500);
        label.setY(500);
        label.setWidth(50);
        label.setHeight(50);
        label.setColor(Color.BLACK);
        // stage.addActor(label);

        Drawable cursor = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor.png"))));
        leftButton = new ImageButton(cursor);
        leftButton.setX(60);
        leftButton.setY(750);
        leftButton.setWidth(50);
        leftButton.setHeight(50);
        leftButton.addListener(new ClickListener());

        Drawable hand = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/hand.png"))));

        centerButton = new ImageButton(hand);
        centerButton.setX(173);
        centerButton.setY(750);
        centerButton.setWidth(50);
        centerButton.setHeight(50);
        centerButton.addListener(new ClickListener());

        Drawable arrow = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/arrow.png"))));

        rightButton = new ImageButton(arrow);
        rightButton.setX(286);
        rightButton.setY(750);
        rightButton.setWidth(50);
        rightButton.setHeight(50);
        rightButton.addListener(new ClickListener());

        stage.addActor(leftButton);
        stage.addActor(centerButton);
        stage.addActor(rightButton);

        Actor a1 = new Actor();
        a1.setName("Jacob");
        Actor a2 = new Actor();
        a2.setName("Ja");
        String[] actors = { a1.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName(), a2.getName(), a2.getName(), a2.getName(),
                a2.getName() };

        List list = new List(actors, uiSkin);

        list.setSize(400, 400);
        list.setColor(Color.BLACK);

        ScrollPane scroll = new BorderScrollPane(list, uiSkin);
        scroll.setPosition(20, 200);
        scroll.setSize(400, 400);
        scroll.setFadeScrollBars(false);
        stage.addActor(scroll);

        list.addCaptureListener(new EventListener() {

            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    InputEvent inputEvent = (InputEvent) event;
                    Gdx.app.log("Handle event", "" + inputEvent.getType());
                }
                return false;
            }

        });

        newActorButton = new TextButton("New", uiSkin);
        newActorButton.setSize(100, 20);
        newActorButton.setPosition(320, 178);
        stage.addActor(newActorButton);

        actorsButton = new TextButton("Actors", uiSkin);
        actorsButton.setSize(150, 20);
        actorsButton.setPosition(20, 602);
        stage.addActor(actorsButton);

        dialogsButton = new TextButton("Dialogs", uiSkin);
        dialogsButton.setSize(150, 20);
        dialogsButton.setPosition(172, 602);
        stage.addActor(dialogsButton);

        placeSceneButtons();
        placeGrid();

        Gdx.input.setInputProcessor(stage);
    }

    public void placeGrid() {
        int x = 400;
        int y = 600;
        for (int i = 0; i < 5; i++) {
            gridButtons.get(i).setSize(50, 50);
            x = x + 50;
            for (int j = 0; j < 5; j++) {
                gridButtons.get(j).setPosition(x, y);
                stage.addActor(gridButtons.get(j));
                y = y - 50;
            }
        }
    }

    public void placeSceneButtons() {
        for (int i = 0; i < sceneButtons.size(); i++) {
            sceneButtons.get(i).setSize(70, 20);
            sceneButtons.get(i).setPosition(452, 701);
            stage.addActor(sceneButtons.get(i));
        }
        newSceneButton.setSize(20, 20);
        newSceneButton.setPosition(452 + 70 * sceneButtons.size() + 1, 701);
        stage.addActor(newSceneButton);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
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
}
