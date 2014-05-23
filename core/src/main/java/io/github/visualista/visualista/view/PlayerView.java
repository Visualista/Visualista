package io.github.visualista.visualista.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * View class for the Player view, responsible for all the visual logic and
 * painting with help from libGdx.
 * 
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 */
public class PlayerView implements ApplicationListener, IPlayerView {

    // Defining static objects and variables //

    private static final float TITLE_FONT_SCALE = 2.0f;
    private static final float STORY_FONT_SCALE = 1.0f;

    private static final float UPPER_BORDER_HEIGHT_RATIO = 1.5f / 10;
    private static final float UPPER_BORDER_WIDTH_RATIO = 2f / 10;
    private static final float UPPER_BORDER_X_DISPLACEMENT_RATIO = 4f / 10;
    private static final float UPPER_BORDER_Y_DISPLACEMENT_RATIO = 8.5f / 10;

    private static final float LOWER_BORDER_HEIGHT_RATIO = 2f / 10;
    private static final float LOWER_BORDER_WIDTH_RATIO = 5f / 10;
    private static final float LOWER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float LOWER_BORDER_Y_DISPLACEMENT_RATIO = 0;

    private static final float LEFT_BORDER_WIDTH_RATIO = 1.5f / 10;
    private static final float LEFT_BORDER_HEIGHT_RATIO = 1f;
    private static final float LEFT_BORDER_X_DISPLACEMENT_RATIO = 0;
    private static final float LEFT_BORDER_Y_DISPLACEMENT_RATIO = 0;

    private static final float RIGHT_BORDER_WIDTH_RATIO = 1.5f / 10;
    private static final float RIGHT_BORDER_HEIGHT_RATIO = 1f;
    private static final float RIGHT_BORDER_X_DISPLACEMENT_RATIO = 7.5f / 10;
    private static final float RIGHT_BORDER_Y_DISPLACEMENT_RATIO = 0;

    private static final float CENTER_BORDER_WIDTH_RATIO = 5f / 10;
    private static final float CENTER_BORDER_HEIGHT_RATIO = 6.5f / 10;
    private static final float CENTER_BORDER_X_DISPLACEMENT_RATIO = 2.5f / 10;
    private static final float CENTER_BORDER_Y_DISPLACEMENT_RATIO = 2f / 10;

    private static final Color RIGHT_BORDER_COLOR = Color.BLACK;
    private static final Color LEFT_BORDER_COLOR = Color.BLACK;
    private static final Color LOWER_BORDER_COLOR = Color.BLACK;
    private static final Color UPPER_BORDER_COLOR = Color.BLACK;
    private static final Color CENTER_BORDER_COLOR = Color.WHITE;

    private static final int CENTER_BORDER_LINE_SIZE = 1;
    // End static objects and variables //

    // Reference variables //
    private IGetPlayerController controller;

    // End reference variables //
    private Stage stage;
    private Matrix<IGetActor> gridButtons;

    private Skin baseSkin;
    private Drawable transparentIcon;

    private Border leftBorder;
    private Border rightBorder;
    private Border upperBorder;
    private Border lowerBorder;
    private Border centerBorder;

    private VerticalGroup leftVerticalGroup;
    private VerticalGroup rightVerticalGroup;
    private VerticalGroup centerVerticalGroup;

    private Dimension configDimension;

    private Image sceneBackgroundImage;

    private Border centerVerticalGroupBorder;
    protected IGetActor selectedActor;

    protected boolean actorFieldHasFocus;

    private boolean isReady = false;

    public PlayerView(Dimension dimension) {
        this.configDimension = dimension;
    }

    @Override
    public final void create() {
        baseSkin = new Skin(Gdx.files.internal("uiskin.json"));
        transparentIcon = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("icons/transparent.png"))));
        createPlayerView();
        isReady = true;
    }

    // Start Create Player //

    private void createPlayerView() {
        stage = new Stage();
        stage.getViewport().setWorldHeight(configDimension.getHeight());
        stage.getViewport().setWorldWidth(configDimension.getWidth());
        stage.clear();

        createLeftBorderContent();
        createRightBorderContent();
        createCenterBorderContent();
        createLowerBorderContent();
        createUppderBorderContent();
        resizeBorders();

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(true);
        controller.openNovelFile();
    }

    private void createCenterBorderContent() {

        // Defining the necessary variables //
        centerVerticalGroup = new VerticalGroup();
        centerBorder = new Border();
        sceneBackgroundImage = new Image();
        centerVerticalGroupBorder = new Border();
        gridButtons = new Matrix<IGetActor>(new Dimension(5, 5));
        // End defining variables //

        // Declaring local variables //
        Stack stack = new Stack();
        // End declaring local variables //

        stage.addActor(centerBorder);
        centerVerticalGroupBorder.setActor(centerVerticalGroup);

        // Adding actors in the right order to the stack //
        stack.add(sceneBackgroundImage);
        stack.add(centerVerticalGroupBorder);
        // End adding actors to stack //

        centerBorder.setActor(stack);

        centerBorder.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                if (controller != null) {
                    controller.openNovelFile();
                } else {
                    // Debug Code //
                    System.out
                            .println("Error: No controller found in PlayerView");
                    // End Debug //
                }
            }
        });

    }

    private void createRightBorderContent() {
        // Defining the necessary variables //
        rightVerticalGroup = new VerticalGroup();
        rightBorder = new Border();
        // End defining variables //

        // Declaring local variables //

        // End declaring local variables //

        // Reference actor to stage //
        stage.addActor(rightBorder);
        rightBorder.setActor(rightVerticalGroup);
        // End referencing //

    }

    private void createLeftBorderContent() {
        // Defining the necessary variables //
        leftVerticalGroup = new VerticalGroup();
        leftBorder = new Border();
        // End defining variables //

        // Declare local variables //

        // End declare local variables //

        // Reference actor to stage //
        stage.addActor(leftBorder);
        leftBorder.setActor(leftVerticalGroup);
        // End referencing //

    }

    private void createLowerBorderContent() {
        // Defining the necessary variables //
        lowerBorder = new Border();
        // End defining variables //
        stage.addActor(lowerBorder);

        updateTextInBorder(lowerBorder, "<<Test>>", TITLE_FONT_SCALE);
    }

    private void createUppderBorderContent() {
        // Defining the necessary variables //
        upperBorder = new Border();
        // End defining variables //
        stage.addActor(upperBorder);

        updateTextInBorder(upperBorder, "<<Test>>", STORY_FONT_SCALE);

    }

    // End Create Player //

    // Logic Methods //
    private void fillGridFromScene(IGetScene scene) {
        gridButtons = new Matrix<IGetActor>(scene.getIGetGrid().getSize());

        for (int i = 0; i < scene.getIGetGrid().getSize().getHeight(); ++i) {
            for (int j = 0; j < scene.getIGetGrid().getSize().getWidth(); ++j) {
                gridButtons.setAt(new Point(i, j),
                        scene.getIGetGrid().getAt(new Point(i, j)).getActor());
            }
        }
        centerVerticalGroup.clearChildren();
        fillGrid(centerVerticalGroup, gridButtons);
    }

    public final void fillGrid(VerticalGroup group, IMatrixGet<IGetActor> data) {
        // Declare local variables //
        float prefferedButtonHeight = group.getHeight()
                / data.getSize().getHeight();
        float prefferedButtonWidth = group.getWidth()
                / data.getSize().getWidth();
        float buttonSide = Math
                .min(prefferedButtonWidth, prefferedButtonHeight);
        // End declare local variables //

        for (int i = 0; i < data.getSize().getHeight(); i++) {
            HorizontalGroup row = new HorizontalGroup();
            group.addActor(row);
            for (int j = 0; j < data.getSize().getWidth(); j++) {
                row.addActor(createSingleTile(data.getAt(new Point(i, j)),
                        prefferedButtonHeight, prefferedButtonWidth));
            }
        }
    }

    private Border createSingleTile(final IGetActor tileActor,
            final float tileSizeHeight, final float tileSizeWidth) {
        Image actorImage;
        actorImage = ModelToGdxHelper.createImageFor(tileActor);
        Image button = actorImage;
        Border tempBorder = new Border();
        tempBorder.setActor(button);
        tempBorder.setSize(tileSizeWidth, tileSizeHeight);
        tempBorder.setLineSize(0);
        tempBorder.setColor(CENTER_BORDER_COLOR);

        // Add Listener to Tile //

        tempBorder.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                if (controller != null) {
                    controller.tileClicked(tileActor);
                } else {
                    // Debug Code //
                    System.out
                            .println("Error: No controller found in PlayerView");
                    // End Debug //
                }
            }
        });

        // End add Listener //

        return tempBorder;
    }

    // End Logic Methods //

    // Resize Methods //
    private void resizeUpperBorder() {
        upperBorder.setSize(UPPER_BORDER_WIDTH_RATIO * stage.getWidth(),
                UPPER_BORDER_HEIGHT_RATIO * stage.getHeight());
        upperBorder.setPosition(
                UPPER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                UPPER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        upperBorder.setColor(UPPER_BORDER_COLOR);

    }

    private void resizeLowerBorder() {
        lowerBorder.setSize(LOWER_BORDER_WIDTH_RATIO * stage.getWidth(),
                LOWER_BORDER_HEIGHT_RATIO * stage.getHeight());
        lowerBorder.setPosition(
                LOWER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                LOWER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        lowerBorder.setColor(LOWER_BORDER_COLOR);

    }

    private void resizeRightBorder() {
        rightBorder.setSize(RIGHT_BORDER_WIDTH_RATIO * stage.getWidth(),
                RIGHT_BORDER_HEIGHT_RATIO * stage.getHeight());
        rightBorder.setPosition(
                RIGHT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                RIGHT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        rightBorder.setColor(RIGHT_BORDER_COLOR);
    }

    private void resizeLeftBorder() {
        leftBorder.setSize(LEFT_BORDER_WIDTH_RATIO * stage.getWidth(),
                LEFT_BORDER_HEIGHT_RATIO * stage.getHeight());
        leftBorder.setPosition(
                LEFT_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                LEFT_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        leftBorder.setColor(LEFT_BORDER_COLOR);

    }

    private void resizeCenterBorder() {
        centerBorder.setSize(CENTER_BORDER_WIDTH_RATIO * stage.getWidth(),
                CENTER_BORDER_HEIGHT_RATIO * stage.getHeight());
        centerBorder.setPosition(
                CENTER_BORDER_X_DISPLACEMENT_RATIO * stage.getWidth(),
                CENTER_BORDER_Y_DISPLACEMENT_RATIO * stage.getHeight());
        centerBorder.setColor(CENTER_BORDER_COLOR);

        centerVerticalGroupBorder.setLineSize(CENTER_BORDER_LINE_SIZE);
        centerVerticalGroupBorder.setSize(centerBorder.getWidth(),
                centerBorder.getHeight());

    }

    private void resizeBorders() {
        resizeCenterBorder();
        resizeLeftBorder();
        resizeRightBorder();
        resizeLowerBorder();
        resizeUpperBorder();
    }

    // End Resize Methods //

    // LibGDX Methods //
    @Override
    public void resize(final int width, final int height) {
        resizeBorders();
    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {
        // Unused method
    }

    @Override
    public void resume() {
        // Unused method
    }

    @Override
    public void dispose() {
        // Unused method
    }

    // End LibGDX Methods //

    // Update Methods //
    @Override
    public void updateScene(IGetScene sceneToDisplay) {
        // Declaring local variables //
        ArrayList<IGetActor> actorsInScene = new ArrayList<IGetActor>();
        actorsInScene.addAll(sceneToDisplay.getActorsInScene());
        String storyText = sceneToDisplay.getStoryText();
        String titleText = sceneToDisplay.getName();
        // End local variables //

        // Background Image //
        if (sceneToDisplay.getImage() != null) {
            final Drawable tile = new TextureRegionDrawable(new TextureRegion(
                    new Texture(sceneToDisplay.getImage().getFileHandle())));

            sceneBackgroundImage.setDrawable(tile);
        } else {
            sceneBackgroundImage.setDrawable(null);
        }
        // End Background Image //

        // Scene Texts //
        updateTextInBorder(upperBorder, titleText, TITLE_FONT_SCALE);
        updateTextInBorder(lowerBorder, storyText, STORY_FONT_SCALE);
        // End Scene Texts //

        // Actor Grid //
        fillGridFromScene(sceneToDisplay);
        // End Actor Grid //

        // Debugging code //
        // String[] actorNames = new
        // String[sceneToDisplay.getActorsInScene().size()];
        // for (int i = 0; i < sceneToDisplay.getActorsInScene().size(); i++) {
        // Gdx.app.log("actorName",
        // ""+sceneToDisplay.getActorsInScene().get(i).getName());
        // actorNames[i] = sceneToDisplay.getActorsInScene().get(i).getName();
        // }
        // End debugging code //
    }

    private void updateTextInBorder(Border borderToUpdate, String storyText,
            float fontScale) {
        borderToUpdate.clearChildren();
        Label label = new Label(storyText, baseSkin);
        label.setFontScale(fontScale);
        borderToUpdate.setActor(label);
    }

    // End Update Methods //

    // Utility Methods //

    @Override
    public boolean getIsReady() {
        return isReady;
    }

    @Override
    public void setController(IGetPlayerController controller) {
        this.controller = controller;
    }

    public void removeFileLoadListeners() {
        Iterator<EventListener> it = centerBorder.getCaptureListeners()
                .iterator();
        while (it.hasNext()) {
            centerBorder.removeCaptureListener(it.next());
        }
    }

    // End Utility Methods //
}
