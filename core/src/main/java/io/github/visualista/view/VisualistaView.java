package io.github.visualista.view;

import io.github.visualista.visualista.core.model.Actor;
import java.io.File;
import java.util.logging.FileHandler;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
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

    private Table table;

    @Override
    public void create() {
        stage = new Stage(1800, 900, true);
        stage.clear();

        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Label label = new Label("Hej", uiSkin);
        label.setX(500);
        label.setY(500);
        label.setWidth(50);
        label.setHeight(50);
        label.setColor(Color.BLACK);
        // stage.addActor(label);

        Drawable cursor = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/cursor2.png"))));
        leftButton = new ImageButton(cursor);
        leftButton.setX(20);
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
        rightButton.setX(326);
        rightButton.setY(750);
        rightButton.setWidth(100);
        rightButton.setHeight(100);
        rightButton.addListener(new ClickListener());

        stage.addActor(leftButton);
        stage.addActor(centerButton);
        stage.addActor(rightButton);

        createTable();

        Actor a1 = new Actor();
        a1.setName("Jacob");
        Actor a2 = new Actor();
        a2.setName("Ja");
        String[] actors = { a1.getName(), a2.getName() };

        List list = new List(actors, uiSkin);
        list.setPosition(20, 0);
        list.setSize(400, 600);
        list.setColor(Color.BLACK);
        ;
        stage.addActor(list);

        newActorButton = new TextButton("New", uiSkin);
        newActorButton.setSize(100, 20);
        newActorButton.setPosition(320, 200);
        stage.addActor(newActorButton);

    }

    public void createTable() {
        /*
         * table = new Table(uiSkin); table.setX(20); table.setY(200);
         * table.setWidth(300); table.setHeight(600);
         * table.setColor(Color.BLACK); table.add(rightButton);
         * stage.addActor(table);
         */
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
