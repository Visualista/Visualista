package io.github.visualista.visualista.view;

import io.github.visualista.visualista.core.FilePickerListener;
import io.github.visualista.visualista.core.IFilePicker;
import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.editorcontroller.IEditorView;
import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.util.Dimension;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

/**
 * View class for the Editor view, responsible for all the visual logic and
 * painting with help from libGdx.
 * 
 * @author Markus Bergland, Erik Risfelt, Pierre Krafft
 */

public class EditorView implements ApplicationListener, IEditorView,
FilePickerListener {

    private static final float HIDDEN_SCENE_WIDTH_RATIO = 0.2f;
    Stage stage;

    Skin uiSkin;

    private java.util.List<Actor> focusableActors;

    Label actionLabel;

    List<IAction> actionList;

    private RightBorder rightBorder;
    UpperBorder upperBorder;
    private LowerBorder lowerBorder;
    private CenterBorder centerBorder;
    VerticalGroup rightVerticalGroup;
    VerticalGroup centerVerticalGroup;

    final ViewEventManager eventManager = new ViewEventManager();

    private final Dimension configDimension;

    private boolean isReady;

    IFilePicker filePicker;

    IGetScene activeScene;

    private TextArea sceneTextArea;

    Border centerVerticalGroupBorder;

    private boolean actorFieldHasFocus;

    protected EditorTool selectedTool;

    final java.util.List<Border> toolButtonBorders;

    LeftBorder leftBorder;

    protected boolean sceneTextAreaHasFocus;

    public EditorView(final Dimension dimension, final IFilePicker filePicker) {
        configDimension = dimension;

        this.filePicker = filePicker;
        toolButtonBorders = new ArrayList<Border>();
    }

    @Override
    public final void create() {
        stage = new Stage();
        stage.getViewport().setWorldHeight(configDimension.getHeight());
        stage.getViewport().setWorldWidth(configDimension.getWidth());

        stage.clear();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        leftBorder = new LeftBorder(this);
        stage.addActor(leftBorder);
        rightBorder = new RightBorder(this);
        stage.addActor(rightBorder);
        lowerBorder = new LowerBorder(this, stage, eventManager);
        stage.addActor(lowerBorder);
        centerBorder = new CenterBorder(this);
        stage.addActor(centerBorder);
        upperBorder = new UpperBorder(this, stage);
        stage.addActor(upperBorder);

        focusableActors = new java.util.ArrayList<Actor>();
        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y,
                    final int pointer, final int button) {
                EditorView.this.objectHit(stage.hit(x, y, true));
                return false;
            }

        });

        // Use if you need to zoom out a bit
        // ((OrthographicCamera) stage.getCamera()).zoom = 2;

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(true);

        isReady = true;
        eventManager.fireViewEvent(this, Type.VIEW_READY);
    }

    // Create Editor //

    private void objectHit(final Actor hitObject) {
        stage.setKeyboardFocus(hitObject);
        stage.setScrollFocus(hitObject);
        // TODO hide objects that want that
    }

    private void addFocusableActorField(final Actor actorField) {
        focusableActors.add(actorField);
    }

    Border surroundWithInvisibleBorder(final Actor actor) {
        Border surroundingBorder = new Border();
        surroundingBorder.setLineSize(0);
        surroundingBorder.setActor(actor);
        return surroundingBorder;
    }

    protected void saveNovel() {
        filePicker.saveFileDialog(this, new FileNameExtensionFilter(
                "Visualista Novel", "vis"));
    }

    protected void openNovel() {
        filePicker.openFileDialog(EditorView.this, new FileNameExtensionFilter(
                "Visualista Novel", "vis"));
    }

    @Override
    public boolean getIsReady() {
        return isReady;
    }

    private float rightSideOf(final com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return actor.getX() + actor.getWidth();
    }

    @Override
    public void resize(final int width, final int height) {
        // stage.setViewport(configDimension.getWidth(),
        // configDimension.getHeight(), true);
        // stage.getCamera().translate(-stage.getGutterWidth(),
        // -stage.getGutterHeight(), 0);

    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    @Override
    public void addViewEventListener(final ViewEventListener eventListener) {
        eventManager.addEventListener(eventListener);
    }

    @Override
    public void removeViewEventListener(final ViewEventListener eventListener) {
        eventManager.removeEventListener(eventListener);
    }

    @Override
    public void addScene(final IGetScene newScene) {
        upperBorder.addNewScene(newScene);
        rightBorder.addNewScene(newScene);
        centerBorder.addNewScene(newScene);

    }

    @Override
    public void updateScene(final IGetScene scene) {
        upperBorder.updateScene(scene);
        centerBorder.updateScene(scene);
        rightBorder.updateScene(scene);
        /*
         * String name = scene.getName(); String text = scene.getStoryText(); if
         * (name == null) { name = ""; } while (name.length() < 5) { name +=
         * " "; } if (text == null) { text = ""; } Gdx.app.log("Scene text",
         * text);
         * 
         * if (scene.getImage() != null) { final Drawable tile = new
         * TextureRegionDrawable(new TextureRegion( new
         * Texture(Gdx.files.absolute(scene.getImage().getFile()
         * .getAbsolutePath()))));
         * 
         * sceneBackgroundImage.setDrawable(tile); } else {
         * sceneBackgroundImage.setDrawable(null); } Tab tab = new Tab(name,
         * uiSkin); tab.addClickListener(this);
         * tab.setHeight(upperBorder.getHeight()); Tab oldTab =
         * tabs.getKey(scene); if (oldTab == null || true) {
         * sceneButtonGroup.addActorBefore(tabUtilityButtons, tab); } else {
         * sceneButtonGroup.addActorBefore(oldTab, tab);
         * sceneButtonGroup.removeActor(oldTab); } tabs.put(tab, scene);
         * hideOverFlowingScenes();
         * 
         * String[] actorNames = new String[scene.getActorsInScene().size()];
         * for (int i = 0; i < scene.getActorsInScene().size(); i++) {
         * actorNames[i] = scene.getActorsInScene().get(i).getName(); }
         * IGetActor[] temp = new IGetActor[scene.getActorsInScene().size()];
         * selectedActor = null;
         * leftBorder.actorList.setItems(scene.getActorsInScene
         * ().toArray(temp)); sceneTextArea.setText(text);
         */
    }

    private static float horizontalDistanceBetween(
            final com.badlogic.gdx.scenes.scene2d.Actor actor1,
            final com.badlogic.gdx.scenes.scene2d.Actor actor2) {
        float distance = actor2.getX() - actor1.getX() - actor1.getWidth();
        if (distance >= 0) {
            return distance;
        }
        distance = actor1.getX() - actor2.getX() - actor2.getWidth();
        if (distance >= 0) {
            return distance;
        } else {
            return 0;
        }
    }

    @Override
    public void removeScene(final IGetScene scene) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeActiveScene(final IGetScene scene) {
        /*
         * updateScene(scene); String name = scene.getName(); if (name == null)
         * { name = ""; } while (name.length() < 5) { name += " "; } Tab tab =
         * new Tab(name, uiSkin); tab.addClickListener(this);
         * tab.setHeight(upperBorder.getHeight()); Tab oldTab =
         * tabs.getKey(scene); if (oldTab != null) { if
         * (hiddenSceneList.getItems().contains(oldTab, true)) {
         * sceneButtonGroup.addActorAt(0, tab);
         * hiddenSceneList.getItems().removeValue(oldTab, true); } else {
         * sceneButtonGroup.addActorBefore(oldTab, tab);
         * sceneButtonGroup.removeActor(oldTab); } } else {
         * sceneButtonGroup.addActorAt(0, tab); } for (Tab aTab :
         * tabs.getAllKeys()) { aTab.useSelectStyle(false); }
         * tab.useSelectStyle(true); tabs.put(tab, scene);
         * hideOverFlowingScenes(); fillGridFromScene(scene);
         */
        // TODO make sure that the order of operation does not matter. Right
        // border will dissapear if its changeActive scene is called after left
        // borders
        upperBorder.changeActiveScene(scene);
        centerBorder.chaneActiveScene(scene);
        rightBorder.changeActiveScene(scene);
        leftBorder.changeActiveScene(scene);
        lowerBorder.changeActiveScene(scene);
        activeScene = scene;

    }

    @Override
    public void changeActiveNovel(final IGetNovel updatedNovel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fileOpened(final File selectedFile) {
        eventManager.fireViewEvent(this, Type.FILE_OPEN, null, selectedFile);
    }

    @Override
    public void fileSaved(final File selectedFile) {
        eventManager.fireViewEvent(this, Type.FILE_SAVE, null, selectedFile);

    }

    @Override
    public void clearModel() {
        clearSceneTabs();
        clearActorList();
        clearActionList();
    }

    private void clearActionList() {
        // actionList.setItems(new String[] {});
    }

    private void clearActorList() {
        // actorList.setItems(new String[] {});

    }

    private void clearSceneTabs() {
        upperBorder.clearSceneTabs();

    }

    @Override
    public void selectActor(final IGetActor actor) {
        rightBorder.selectActor(actor);
        centerBorder.selectActor(actor);
    }

    @Override
    public void updateActor(final IGetActor updatedActor) {
        rightBorder.updateActor(updatedActor);
    }

    @Override
    public void updateTile(final Object updatedObject, final IGetTile updatedTile) {
        ((Image) updatedObject).setDrawable(ModelToGdxHelper
                .createDrawableFor(updatedTile));

    }

    @Override
    public void addNewActor(final IGetActor actor) {
        leftBorder.addNewActor(actor);
        rightBorder.addNewActor(actor);
    }

    @Override
    public void updateActionList(final IGetActor updatedActor) {
        rightBorder.updateActionList(updatedActor);

    }
}
