package io.github.visualista.visualista.core;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class VisualistaEditor implements ApplicationListener,
		FilePickerListener {
	private Stage stage;
	private FilePicker filePicker;

	public VisualistaEditor(FilePicker filePicker) {
		this.filePicker = filePicker;
	}

	@Override
	public void create() {
		stage = new Stage(1800, 900, true);
		stage.clear();

		// Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json.txt"));
		// Label label = new Label("Hej", uiSkin);
		// label.setX(500);
		// label.setY(500);
		// label.setWidth(100);
		// label.setHeight(100);
		// stage.addActor(label);

		Drawable cursor = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/cursor.png"))));
		ImageButton leftButton = new ImageButton(cursor);
		leftButton.setX(69);
		leftButton.setY(100);
		leftButton.setWidth(100);
		leftButton.setHeight(100);
		stage.addActor(leftButton);

		Drawable hand = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/hand.png"))));
		ImageButton centerButton = new ImageButton(hand);
		leftButton.setX(171);
		leftButton.setY(100);
		leftButton.setWidth(100);
		leftButton.setHeight(100);
		stage.addActor(centerButton);

		Drawable arrow = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/arrow.png"))));
		ImageButton rightButton = new ImageButton(arrow);
		leftButton.setX(273);
		leftButton.setY(100);
		leftButton.setWidth(100);
		leftButton.setHeight(100);
		stage.addActor(rightButton);
		leftButton.addListener(new EventListener(){
			
			@Override
			public boolean handle(Event event) {
				if(event instanceof InputEvent){
					InputEvent inputEvent = (InputEvent)event;
					if(inputEvent.getType() == InputEvent.Type.touchDown){
						filePicker.fileDialog(VisualistaEditor.this, true, new FileNameExtensionFilter("Visualista Novel", "vis"));
					}
					return true;
				}
				
				return false;
			}
			
		});
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		stage.act();
		Gdx.gl.glClearColor(1, 1, 1, 2);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void filePicked(File selectedFile, boolean fileOpen) {
		if (fileOpen) {
			Gdx.app.log("VisulistaEditor", "Opened file: " + selectedFile);
		} else {
			Gdx.app.log("VisulistaEditor", "Saved file: " + selectedFile);
		}

	}

}
