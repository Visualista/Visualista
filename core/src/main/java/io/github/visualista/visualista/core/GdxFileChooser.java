/**
@Title: A File Chooser for Libgdx.
@Date: August 2012.
@Author: fb.com/CrazyCloud8

Licensed under the Apache License, Version 2.0;
*/

package io.github.visualista.visualista.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** This may be a directory chooser, not a file chooser. Take care. */
public class GdxFileChooser implements Screen {

    /** This is what a Caller should implement. */
    public interface Callback {
        /** the choosed directory */
        public void choose(String directory);

        /** nothing to choose */
        public void cancel();
    }

    Array<String> items;
    // public Array<String> getItems() {
    // return items;
    // }

    private final static boolean debug = true;
    private GdxFileChooser.Callback callback;

    private Stage stage;
    private Table root;
    private Table content;

    private Skin skin;
    private Label fpsLabel;

    private String directory;
    private String[] fileFilter;

    private NinePatch patch;
    private TextField textfield;
    // private TextButton ok, cancel;
    private ImageButton ok, cancel;
    private ImageButton back;
    private ImageButton refresh;
    // private SelectBox lastDir;

    private List list;
    private SpriteBatch batch;

    private GdxFileChooser() {/*No No*/}

    /** normally, you would like this */
    public GdxFileChooser(GdxFileChooser.Callback c, SpriteBatch b) {
        this.callback = c;
        this.batch = b;
    }

    /** this is good for testing, creates own SpriteBatch */
    public GdxFileChooser(GdxFileChooser.Callback c) {
        callback = c;
        batch = new SpriteBatch();
    }

    /** may be later */
    public void setFilter(String directory, String... fileFilter) {
        this.directory = directory;
        this.fileFilter = fileFilter;
    }

    private String getParentPath(String dir) {
        if (dir==null) return "";
        int x = dir.lastIndexOf('/');
        if (x>0) return dir.substring(0,x);
        return dir;
    }

    /** to make desktop more equal */
    private String getMyPath(String path) {
        if (Gdx.app.getType() == ApplicationType.Desktop)
            return "assets/" + path;
        return path;
    }

    //@Override
    public void create() {
        items = new Array<String>();
        if(debug)
        	Gdx.app.log("GdxFileChooser", "asset folder "+Gdx.files.internal("data/uiskin.json").exists());
        
     // Creates our atlas from our png file containing all the sprites
        TextureAtlas atlas = new TextureAtlas("uiskin.atlas");

        // Create a stage, everything goes on the stage
        stage = new Stage();
        // A skin for the stage
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
        
        stage = new Stage(Gdx.graphics.getWidth(),
                          Gdx.graphics.getHeight(), false, batch);
        patch = skin.getPatch("default-round");
        root = new Table();
        root.setBackground(new NinePatchDrawable(patch));
        content = new Table();

        textfield = new TextField("", skin);
        if(debug)
        	Gdx.app.log("GdxFileChooser", "textfield set "+textfield);
        textfield.setMessageText("Do not click here!");
        textfield.setTextFieldListener(new TextFieldListener() {
            public void keyTyped (TextField textField, char key) {
                if (key == '\n') {
                    textField.getOnscreenKeyboard().show(false);
                    action(-5);
                }
            }
        });

        refresh = createImageButton("cursor.png");
        refresh.addListener(new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 action(-4);
             }
        });

        // ok = new TextButton("OK", skin);
        ok = createImageButton("hand.png");
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action(-3);
            }
        });

        // cancel = new TextButton("Cancel", skin);
        cancel = createImageButton("hand.png");
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action(-2);
            }
        });

        back = createImageButton("hand.png");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action(-1);
            }
        });

     // Texture checkOnTex = new Texture(Gdx.files.internal(
     // getMyPath("data/icons/btn_check_on.png")));
     // Texture checkOffTex = new Texture(Gdx.files.internal(
     // getMyPath("data/icons/btn_check_off.png")));
     // TextureRegion checkOnTR = new TextureRegion(checkOnTex);
     // TextureRegion checkOffTR = new TextureRegion(checkOffTex);
     // TextureRegion checkOffTRFlipped = new TextureRegion(checkOffTex);
     // checkOffTRFlipped.flip(true, false);
     // ImageButtonStyle it = new ImageButtonStyle(skin.get(ButtonStyle.class));
     // it.imageUp = new TextureRegionDrawable(checkOffTR);
     // it.imageDown = new TextureRegionDrawable(checkOffTRFlipped);
     // it.imageChecked = new TextureRegionDrawable(checkOnTR);
     // check = new ImageButton(it.imageUp, it.imageDown, it.imageChecked);
     // // 0.1f, 0.1f, 0.1f, 0.1f,
     // // it.imageChecked, it.imageDown, it.imageUp);
     // check.addListener(new ClickListener() {
     // @Override
     // public void clicked(InputEvent event, float x, float y) {
     // action(-5);
     // }
     // });

        fpsLabel = new Label("fps:", skin);
        root.setFillParent(true);

        if (debug)
            root.debug();
        stage.addActor(root);
        if (debug)
            Gdx.app.log("GdxFileChooser", "created: [" + directory + "]");
    }

    private ImageButton createImageButton(String name) {
        Texture texture = new Texture(
                      Gdx.files.internal(getMyPath("data/icons/" + name)));
        TextureRegion tRegion = new TextureRegion(texture);
        TextureRegion tFlip = new TextureRegion(tRegion);
        tFlip.flip(true, true);
        ImageButtonStyle is = new ImageButtonStyle(skin.get(ButtonStyle.class));
        is.imageUp = new TextureRegionDrawable(tRegion);
        is.imageDown = new TextureRegionDrawable(tFlip);
        return new ImageButton(is.imageUp, is.imageDown);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor( stage );
        if (debug)
            Gdx.app.log("GdxFileChooser", "show " + directory);
        if(debug)
        	Gdx.app.log("GdxFileChooser", "textfield "+textfield);
        textfield.setMessageText( ""+directory );
        textfield.setText( ""+directory );

        root.clear();
        // Table content = getContent(directory); // this is no ponyhof
        content = getContent(directory);
        root.setClip(true);
        root.setSkin(skin);
        root.bottom();

        ScrollPane scroll = new ScrollPane(content, skin);
        root.add(scroll).colspan(4).fill();

        root.row();
        root.add(textfield).colspan(4).expandX().fill();
        root.row();

        root.add(ok);
        root.add(refresh);
        root.add(back);
        root.add(cancel);
        root.add(fpsLabel);
        root.pack();
        root.layout();
    }

    /** eventually callbacks the caller */
    private void action(int what) {
        if (debug)
            Gdx.app.log("GdxFileChooser", "action " + what);

        // back button : go to parent directory
        if (what==-1) {
            if (debug)
                Gdx.app.log("GdxFileChooser", "action back " + what);
            directory = getParent(directory);
            show();
            return;
        }
        // cancel button : call back
        if (what==-2) {
            if (debug)
                Gdx.app.log("GdxFileChooser", "action cancel " + what);
            callback.cancel();
            return;
        }
        // ok button
        if (what==-3) {
            if (debug)
Gdx.app.log("GdxFileChooser", "ok [" + directory + "]");
            loadItems(directory);
            callback.choose(directory);
            return;
        }
// refresh
        if (what==-4) {
            if (debug)
Gdx.app.log("GdxFileChooser", "refresh [" + directory + "]");
show();
            return;
        }
        // manual text edit
        if (what==-5) {
            directory = textfield.getText();
            if (directory.startsWith("http:"))
callback.choose(directory);
else show();
            return;
        }
        if (what<0) {
            if (debug)
                Gdx.app.log("GdxFileChooser", "strange action " + what);
            return;
        }
        if (what<items.size) {
            String whats = items.get(what);
            if (whats.startsWith("http://")) {
callback.choose(whats);
                return;
            }

            directory = getChild( items.get(what) );
            textfield.setText( directory );
        }
        show();
    }

    private String getParent(String directory) {
        if ( directory.lastIndexOf("/")>0 )
directory = directory.substring(0, directory.lastIndexOf("/"));
else directory = "";
        return directory;
    }

private String getChild(String child) {
        // FileHandle check = Gdx.files.absolute(directory + "/" + child);
        FileHandle check = Gdx.files.external(directory + "/" + child);
        if (check.isDirectory()) {
            directory = directory + "/" + child;
}
        if (debug)
            Gdx.app.log("GdxFileChooser", "selected " + directory + "]");
return directory;
}

    public void render(/* so what */) {
        Gdx.gl.glViewport(0, 0,
        Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (debug) Table.drawDebug(stage);
    }

    private Table getContent(String directory) {
        content = new Table(); //make new Table prevents exception
        // content.clear(); // may be later.
        content.left();
        List list = getList(directory);
        content.row();
        content.add(list);
        return content;
    }

    private List getList(String directory) {
        items = getItems(directory);
        list = new List(items.toArray(), skin);
        list.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    action(list.getSelectedIndex());
                }
        });
        return list;
    }

/**
private Array<String> getItems(String directory, int start, int off) {
items.clear();
FileHandle[] files = Gdx.files.absolute(directory).list();
if(files==null)
return items;
int i=0;
for(FileHandle f:files) {
if (f.isDirectory()) {
if (i<start) continue;
if (++i>off) break;
items.add(f.name());
}
}
if (i<off) for(FileHandle f:files) {
if (!f.isDirectory()) {
if (++i>off) break;
items.add(f.name());
}
}
return items;
}
**/

    private Array<String> getItems(String directory) {
        items.clear();
        // FileHandle[] files = Gdx.files.absolute(directory).list();
        FileHandle[] files = Gdx.files.external(directory).list();
        getItems(files);
        // if (items.size<5) {
        // items.add("http://localhost/~goetz/liste.php?format=json");
        // }
        if (items.size==0) {
             items.add("Nothing found here.");
}
        return items;
    }

    //GH201208
    private void getItems(FileHandle[] files) {
        // if(files==null) return ;
        for(FileHandle f:files) {
            if (f.isDirectory()) {
if (f.name().startsWith(".")) ; // hidden
                else items.add(f.name());
            } else
            for (int i=0; i<fileFilter.length; i++) {
                 if (f.name().endsWith(fileFilter[i]))
                     items.add(f.name());
            }
        }
    }

    public void loadItems(String directory) {
        items.clear();
        FileHandle[] files = Gdx.files.external(directory).list();
        for(FileHandle f:files) {
            for (int i=0; i<fileFilter.length; i++) {
                if (f.name().endsWith(fileFilter[i]))
                    items.add(directory + "/" + f.name());
            }
        }
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void hide() { root.clear(); }

    @Override
    public void render(float f) { render(); }

    @Override
    public void resize (int width, int height) {
        stage.setViewport(width, height, false);
    }

    @Override
    public void dispose () {
        Gdx.app.log("GdxFileChooser", "disposed");
        stage.dispose();
        skin.dispose();
    }
}