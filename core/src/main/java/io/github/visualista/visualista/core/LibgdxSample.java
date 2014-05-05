package io.github.visualista.visualista.core;

import java.io.File;
import java.util.logging.FileHandler;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

public class LibgdxSample implements ApplicationListener {
	private Stage stage;

	@Override
	public void create() {
		stage = new Stage(900, 900, true);
		stage.clear();

		Drawable cursor = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/hand.png"))));
		ImageButton leftButton = new ImageButton(cursor);
		leftButton.setX(500);
		leftButton.setY(500);
		leftButton.setWidth(200);
		leftButton.setHeight(200);
		stage.addActor(leftButton);

		Drawable hand = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/hand.png"))));
		ImageButton centerButton = new ImageButton(hand);
		leftButton.setX(271);
		leftButton.setY(200);
		leftButton.setWidth(100);
		leftButton.setHeight(100);
		stage.addActor(centerButton);

		Drawable arrow = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/arrow.png"))));
		ImageButton rightButton = new ImageButton(arrow);
		leftButton.setX(473);
		leftButton.setY(400);
		leftButton.setWidth(100);
		leftButton.setHeight(100);
		stage.addActor(rightButton);
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
