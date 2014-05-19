package io.github.visualista.visualista.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import io.github.visualista.visualista.editorcontroller.ViewEventManager;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.playercontroller.IPlayerView;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.util.ArrayList;

public class PlayerView implements ApplicationListener, IPlayerView{
    
    //Defining static objects and variables //
    private static final float SIDE_BORDER_RATIO = 1.5f / 9;
    private static final Skin BASE_SKIN = new Skin( Gdx.files.internal( "uiskin.json" ) );
    private static final Drawable TRANSPARENT_ICON = new TextureRegionDrawable(
            new TextureRegion( 
                    new Texture( Gdx.files.internal( "icons/transparent.png" ) ) ) );
    private static final float TITLE_FONT_SCALE = 2.0f;
    private static final float STORY_FONT_SCALE = 1.0f;
    // End static objects and variables //

    // Reference variables //
    private IGetPlayerController controller;
    
    // End reference variables //
    private Stage stage;
    private Matrix<IGetActor> gridButtons;

    private Border leftBorder;
    private Border rightBorder;
    private Border upperBorder;
    private Border lowerBorder;
    private Border centerBorder;

    private VerticalGroup leftVerticalGroup;
    private VerticalGroup rightVerticalGroup;
    private VerticalGroup centerVerticalGroup;


    private final ViewEventManager eventManager = new ViewEventManager();

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
        createPlayerView();
    }
    
    
  
    // Start Create Player //

    private void createPlayerView(){
        stage = new Stage();
        stage.getViewport().setWorldHeight( configDimension.getHeight() );
        stage.getViewport().setWorldWidth( configDimension.getWidth() );
        stage.clear();
        
        createLeftBorderContent();
        createRightBorderContent();
        createCenterBorderContent();
        createLowerBorderContent();
        createUppderBorderContent();
        resizeCenterBorder();
        
        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(false);
        isReady = true;
    }
    
    private void createCenterBorderContent(){
        
        // Defining the necessary variables //
        centerVerticalGroup = new VerticalGroup();
        centerBorder = new Border();
        sceneBackgroundImage = new Image();
        centerVerticalGroupBorder = new Border();
        gridButtons = new Matrix<IGetActor>( new Dimension( 5, 5 ) );
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
        
        
    }

    private void createRightBorderContent(){
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
        
        // Define appearance of right border //
        rightBorder.setSize( stage.getWidth() * SIDE_BORDER_RATIO, stage.getHeight() );
        rightBorder.setPosition( stage.getWidth() - rightVerticalGroup.getWidth(), 0 );
        rightBorder.setColor(Color.BLACK);
        // End defining appearance //
        
    }
    
    private void createLeftBorderContent(){
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
        
        // Define appearance of left border //¨
        leftBorder.setSize( stage.getWidth() * SIDE_BORDER_RATIO, stage.getHeight() );
        leftBorder.setPosition(0, 0);
        leftBorder.setColor(Color.BLACK);
        // End defining appearance //
        
    }
    
    private void createLowerBorderContent() {
        // Defining the necessary variables //
        lowerBorder = new Border();
        // End defining variables //
        // Define appearance of lower border //
        lowerBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                100);
        lowerBorder.setPosition(getRightSideOf(leftBorder), 0);
        // End defining appearance //
        stage.addActor(lowerBorder);
        
        updateTextInBorder(lowerBorder, "<<Test>>", TITLE_FONT_SCALE);
    }
    
    private void createUppderBorderContent(){
        // Defining the necessary variables //
        upperBorder = new Border();
        // End defining variables //
        // Define appearance of lower border //
        upperBorder.setSize(horizontalDistanceBetween(leftBorder, rightBorder),
                100);
        upperBorder.setPosition(getRightSideOf(leftBorder), 0);
        // End defining appearance //
        stage.addActor(upperBorder);
        
        updateTextInBorder(upperBorder, "<<Test>>", STORY_FONT_SCALE);
        
    }

    // End Create Player //
    
    // Update Player //
    
    // End Update Player //
    private void fillGridFromScene(IGetScene scene) {
        gridButtons = new Matrix<IGetActor>(scene.getIGetGrid().getSize());
        
        for (int i = 0; i < scene.getIGetGrid().getSize().getHeight(); ++i) {
            for (int j = 0; j < scene.getIGetGrid().getSize().getWidth(); ++j) {
                gridButtons.setAt(new Point( i, j ), scene.getIGetGrid().getAt( 
                        new Point (i, j) ).getActor() );
            }
        }
        fillGrid(centerVerticalGroup, gridButtons);
    }

    private void resizeCenterBorder() {
        centerBorder.setSize(
                horizontalDistanceBetween(leftBorder, rightBorder),
                (upperBorder.getY() - lowerBorder.getY()
                        - lowerBorder.getHeight()));
        centerBorder.setPosition(getRightSideOf(leftBorder), lowerBorder.getY()
                + lowerBorder.getHeight());
        centerVerticalGroupBorder.setLineSize(0);
        centerVerticalGroupBorder.setSize(centerBorder.getWidth(),
                centerBorder.getHeight());

    }

    public final void fillGrid(VerticalGroup group, IMatrixGet<IGetActor> data) {
        // Declare local variables //
        float prefferedButtonHeight = group.getHeight()
                / data.getSize().getHeight();
        float prefferedButtonWidth = group.getWidth()
                / data.getSize().getWidth();
        float buttonSide = Math.min(prefferedButtonWidth,
                prefferedButtonHeight);
        // End declare local variables //
        
        for (int i = 0; i < data.getSize().getHeight(); i++) {
            HorizontalGroup row = new HorizontalGroup();
            group.addActor(row);
            for (int j = 0; j < data.getSize().getWidth(); j++) {               
                row.addActor(createSingleTile(data.getAt(new Point(i, j)), buttonSide));
            }
        }
    }

    private Border createSingleTile(final IGetActor tileActor, final float tileSize){
        Drawable actorImage;
        if (tileActor.getImage() != null){
            actorImage = new TextureRegionDrawable( new TextureRegion( 
                    new Texture ( Gdx.files.absolute( 
                            tileActor.getImage().getFile().getAbsolutePath() ) ) ) );
            
        } else {
            actorImage = TRANSPARENT_ICON;
        }
        Image button = new Image(actorImage);
        Border tempBorder = new Border();
        tempBorder.setActor(button);
        tempBorder.setSize(tileSize, tileSize);
        tempBorder.setLineSize(0);
        
        // Add Listener to Tile //
        
        tempBorder.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                if (controller != null){
                    controller.tileClicked(tileActor);
                } else {
                    System.out.println("Error: No controller found in PlayerView");
                }
                }
        });
        
        // End add Listener //
        
        return tempBorder;
    }
    @Override
    public void resize(final int width, final int height) {

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
                    new Texture(Gdx.files.absolute(sceneToDisplay.getImage().getFile()
                            .getAbsolutePath()))));

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
        String[] actorNames = new String[sceneToDisplay.getActorsInScene().size()];
        for (int i = 0; i < sceneToDisplay.getActorsInScene().size(); i++) {
            Gdx.app.log("actorName", ""+sceneToDisplay.getActorsInScene().get(i).getName());
            actorNames[i] = sceneToDisplay.getActorsInScene().get(i).getName();
        }
        // End debugging code //
    }
    
    private void updateTextInBorder(Border borderToUpdate, String storyText, float fontScale){
        borderToUpdate.clear();
        Label label = new Label(storyText, BASE_SKIN);
        label.setFontScale(fontScale);
        borderToUpdate.setActor(label);
    }
    // End Update Methods //
    
    // Utility Methods //
    private float getRightSideOf(com.badlogic.gdx.scenes.scene2d.Actor viewActor) {
        return viewActor.getX() + viewActor.getWidth();
    }
    
    private static float horizontalDistanceBetween(
            com.badlogic.gdx.scenes.scene2d.Actor actor1,
            com.badlogic.gdx.scenes.scene2d.Actor actor2) {
        float distance = actor2.getX() - actor1.getX() - actor1.getWidth();
        if (distance >= 0)
            return distance;
        {
        }
        distance = actor1.getX() - actor2.getX() - actor2.getWidth();
        if (distance >= 0) {
            return distance;
        } else {
            return 0;
        }
    }

    @Override
    public boolean getIsReady() {
        return isReady;
    }
    
    @Override
    public void addController(IGetPlayerController controller){
        this.controller = controller;
    }
    // End Utility Methods //
}
