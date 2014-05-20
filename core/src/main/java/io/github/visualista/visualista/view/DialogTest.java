package io.github.visualista.visualista.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class DialogTest implements ApplicationListener {

    private Stage stage;

    public DialogTest() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void create() {
        stage = new Stage();
        stage.getViewport().setWorldHeight(700);
        stage.getViewport().setWorldWidth(1200);
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Label label = new Label("Tap tiles to build a word and then press the blue button to play the word", uiSkin);
        label.setWrap(true);
        label.setFontScale(.8f);
        label.setAlignment(Align.center);

        Dialog dialog = 
        new Dialog("", uiSkin, "dialog") {
            protected void result (Object object) {
                    System.out.println("Chosen: " + object);
            }
        };

        dialog.padTop(50).padBottom(50);
        dialog.getContentTable().add(label).width(850).row();
        dialog.getButtonTable().padTop(50);

        Button dbutton = new TextButton("Yes",uiSkin, "default");
        dialog.button(dbutton, true);

        dbutton = new TextButton("No", uiSkin, "default");
        dialog.button(dbutton, false);
        dialog.key(Keys.ENTER, true).key(Keys.ESCAPE, false);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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

}
