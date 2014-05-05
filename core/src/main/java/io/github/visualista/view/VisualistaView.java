package io.github.visualista.view;

import io.github.visualista.visualista.core.model.Actor;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

public class VisualistaView implements ApplicationListener {
	private Stage stage;

	private Skin uiSkin;

	private ImageButton leftButton;
	private ImageButton centerButton;
	private ImageButton rightButton;
	private ImageButton actorImage;

	private TextButton newActorButton;
	private TextButton actorsButton;
	private TextButton dialogsButton;
	private TextButton newSceneButton;
	private TextButton modifyButton;
	private TextButton addActionButton;

	private Label actorLabel;
	private Label actionLabel;

	private ArrayList<TextButton> sceneButtons;
	private ArrayList<ImageButton> gridButtons;

	private List actorList;
	private List actionList;

	private Border leftBorder;
	private Border rightBorder;
	private Border upperBorder;
	private Border lowerBorder;

	// private TextureAtlas atlas;

	@Override
	public void create() {
		stage = new Stage(1800, 900, true);
		stage.clear();

		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

		gridButtons = new ArrayList<ImageButton>();
		Drawable tile = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/tile.png"))));

		for (int i = 0; i < 25; i++) {
			gridButtons.add(i, new ImageButton(tile));
		}

		newSceneButton = new TextButton("+", uiSkin);

		newSceneButton.addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				InputEvent inputEvent = (InputEvent) event;
				sceneButtons.add(new TextButton("Scene"
						+ (sceneButtons.size() + 1), uiSkin));
				placeSceneButtons();
			}
		});

		sceneButtons = new ArrayList<TextButton>();
		sceneButtons.add(new TextButton("Scene1", uiSkin));

		leftBorder = new Border(null);
		leftBorder.setSize(450, 1800);
		leftBorder.setPosition(0, 0);
		stage.addActor(leftBorder);

		rightBorder = new Border(null);
		rightBorder.setSize(450, 1800);
		rightBorder.setPosition(1350, 0);
		stage.addActor(rightBorder);

		upperBorder = new Border(null);
		upperBorder.setSize(900, 200);
		upperBorder.setPosition(450, 700);
		stage.addActor(upperBorder);

		lowerBorder = new Border(null);
		lowerBorder.setSize(900, 200);
		lowerBorder.setPosition(450, 0);
		stage.addActor(lowerBorder);

		actorLabel = new Label("Hej", uiSkin);
		actorLabel.setPosition(1550, 750);
		actorLabel.setSize(50, 50);
		actorLabel.setColor(Color.BLACK);
		stage.addActor(actorLabel);

		actionLabel = new Label("Actions", uiSkin);
		actionLabel.setPosition(1550, 500);
		actionLabel.setSize(50, 50);
		actionLabel.setColor(Color.BLACK);
		stage.addActor(actionLabel);

		Drawable cursor = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/cursor.png"))));
		leftButton = new ImageButton(cursor);
		leftButton.setPosition(60, 750);
		leftButton.setSize(50, 50);
		leftButton.addListener(new ClickListener());

		Drawable hand = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/hand.png"))));

		centerButton = new ImageButton(hand);
		centerButton.setPosition(173, 750);
		centerButton.setSize(50, 50);
		centerButton.addListener(new ClickListener());

		Drawable arrow = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/arrow.png"))));

		rightButton = new ImageButton(arrow);
		rightButton.setPosition(286, 750);
		rightButton.setSize(50, 50);
		rightButton.addListener(new ClickListener());

		stage.addActor(leftButton);
		stage.addActor(centerButton);
		stage.addActor(rightButton);

		Actor a1 = new Actor();
		a1.setName("Jacob");
		Actor a2 = new Actor();
		a2.setName("Ja");
		String[] actors = { a1.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName(), a2.getName(), a2.getName(), a2.getName(),
				a2.getName() };

		actorList = new List(actors, uiSkin);

		actorList.setSize(400, 400);
		actorList.setColor(Color.BLACK);

		ScrollPane scroll = new BorderScrollPane(actorList, uiSkin);
		scroll.setPosition(20, 200);
		scroll.setSize(400, 400);
		scroll.setFadeScrollBars(false);
		stage.addActor(scroll);

		actorList.addCaptureListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					// Gdx.app.log("Handle event", "" + inputEvent.getType());
				}
				return false;
			}

		});

		actionList = new List(actors, uiSkin);

		actionList.setSize(350, 400);
		actionList.setColor(Color.BLACK);

		ScrollPane actionScroll = new BorderScrollPane(actionList, uiSkin);
		actionScroll.setPosition(1368, 100);
		actionScroll.setSize(400, 400);
		actionScroll.setFadeScrollBars(false);
		stage.addActor(actionScroll);

		actionList.addCaptureListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					// Gdx.app.log("Handle event", "" + inputEvent.getType());
				}
				return false;
			}

		});

		newActorButton = new TextButton("New", uiSkin);
		newActorButton.setSize(100, 20);
		newActorButton.setPosition(320, 178);
		stage.addActor(newActorButton);

		actorsButton = new TextButton("Actors", uiSkin);
		actorsButton.setSize(150, 20);
		actorsButton.setPosition(20, 602);
		stage.addActor(actorsButton);

		dialogsButton = new TextButton("Dialogs", uiSkin);
		dialogsButton.setSize(150, 20);
		dialogsButton.setPosition(172, 602);
		stage.addActor(dialogsButton);

		modifyButton = new TextButton("Modify", uiSkin);
		modifyButton.setSize(150, 20);
		modifyButton.setPosition(1368, 75);
		stage.addActor(modifyButton);

		addActionButton = new TextButton("Add", uiSkin);
		addActionButton.setSize(150, 20);
		addActionButton.setPosition(1520, 75);
		stage.addActor(addActionButton);

		Drawable actorDraw = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("icons/cursor.png"))));
		actorImage = new ImageButton(actorDraw);
		actorImage.setSize(70, 70);
		actorImage.setPosition(1530, 650);
		stage.addActor(actorImage);

		placeSceneButtons();
		placeGrid();

		Gdx.input.setInputProcessor(stage);
	}

	public void placeGrid() {
		int y = 580;
		int x = 700;
		for (int i = 0; i < 25; i++) {
			gridButtons.get(i).setSize(80, 80);
			gridButtons.get(i).setPosition(x, y);
			stage.addActor(gridButtons.get(i));

			if (i == 4 || i == 9 || i == 14 || i == 19) {
				x = x + 80;
				y = 580;
			} else
				y = y - 80;

		}

	}

	public void placeSceneButtons() {
		int x = 452;
		for (int i = 0; i < sceneButtons.size(); i++) {
			sceneButtons.get(i).setSize(70, 20);
			sceneButtons.get(i).setPosition(x, 701);
			x = x + 70;

			stage.addActor(sceneButtons.get(i));
		}
		newSceneButton.setSize(20, 20);
		newSceneButton.setPosition(452 + 70 * sceneButtons.size() + 1, 701);
		stage.addActor(newSceneButton);
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