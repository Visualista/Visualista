package io.github.visualista.visualista.core;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class LibgdxSample implements ApplicationListener {
    private Stage stage;
    private VerticalGroup leftGroup;
    private VerticalGroup rightGroup;

    @Override
    public void create() {
        stage = new Stage(1900, 1000, true);
        stage.clear();
        
        Label label = new Label("h",new Skin());
        
        leftGroup = new VerticalGroup();
        rightGroup = new VerticalGroup();
        stage.addActor(leftGroup);
        stage.addActor(rightGroup);
        leftGroup.addActor(label);
        Skin uiSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Label l = new Label("H",uiSkin);
        leftGroup.addActor(l);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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
