package io.github.visualista.visualista.core;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
        
        leftGroup = new VerticalGroup();
        rightGroup = new VerticalGroup();
        stage.addActor(leftGroup);
        stage.addActor(rightGroup);
      
        Drawable draw = new TextureRegionDrawable(new TextureRegion(new Texture("libgdx-logo.png")));
        ImageButton button = new ImageButton(draw);
        button.setX(500);
        button.setY(500);
        button.setWidth(250);
        button.setHeight(200);
        stage.addActor(button);
        button.setBackground(draw);
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
