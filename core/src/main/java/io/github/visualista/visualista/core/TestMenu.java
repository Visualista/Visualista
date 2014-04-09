package io.github.visualista.visualista.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TestMenu implements ApplicationListener{

	private Stage stage;
	
	@Override
	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		createTestObjects();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
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
		stage.dispose();
		
	}
	
	
	//----------------------------------------------------------------------------//
	
	private void createTestObjects(){
		
		Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Table table = new Table();
		table.setFillParent(true);
		
		TextButton tb1 = new TextButton("Test", uiSkin);
		table.addActor(tb1);
		
		stage.addActor(table);
	}

}
