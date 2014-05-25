package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.ViewEventListener;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.util.BiDiMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;

class UpperBorder extends Border implements Updateable, TabListener {

    private static final int TAB_MINIMUM_TEXT_LENGTH = 5;

    // Declaring static variables //
    private static final float HIDDEN_SCENE_X_DISPLACEMENT_RATIO = 0.7f;

    private static final float HIDDEN_SCENE_Y_DISPLACEMENT_RATIO = 0.7f;

    private static final float HIDDEN_SCENE_WIDTH_RATIO = 0.3f;

    private static final float HIDDEN_SCENE_HEIGHT_RATIO = 0.2f;

    private static final int UPPER_BORDER_LINE_SIZE = 1;

    private static final Color UPPER_BORDER_COLOR = Color.BLACK;

    private static final float UPPER_BORDER_Y_DISPLACEMENT_RATIO = 0.95f;

    private static final float UPPER_BORDER_X_DISPLACEMENT_RATIO = 0.25f;

    private static final float UPPER_BORDER_WIDTH_RATIO = 0.5f;

    private static final float UPPER_BORDER_HEIGHT_RATIO = 0.05f;

    private List<Tab> hiddenSceneList;

    private final BiDiMap<Tab, IGetScene> tabs;

    private HorizontalGroup sceneButtonGroup;

    private HorizontalGroup tabUtilityButtons;

    private final Skin uiSkin;

    private IGetScene activeScene;

    private final ViewEventListener eventManager;

    private ScrollPane newScrollPane;

    public UpperBorder(final Skin skin, final ViewEventListener eventManager) {
        uiSkin = skin;
        tabs = new BiDiMap<Tab, IGetScene>();
        setActor(createUpperBorderContent());
        this.eventManager = eventManager;
    }

    public void changeActiveScene(final IGetScene scene) {
        Tab tab = tabs.getKey(scene);
        bringTabToTabGroup(tab);
        for (Tab aTab : tabs.getAllKeys()) {
            aTab.useSelectStyle(false);
        }
        tab.useSelectStyle(true);
        // tabs.put(tab, scene);
        // hideOverFlowingScenes();
        // fillGridFromScene(scene);

    }

    private void bringTabToTabGroup(final Tab tab) {
        int index = hiddenSceneList.getItems().indexOf(tab, true);
        if (index != -1) {
            hiddenSceneList.getItems().removeIndex(index);
            sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
        }

    }

    public void addNewScene(final IGetScene scene) {
        String name = getPaddedSceneName(scene);
        Tab tab = new Tab(name, uiSkin);
        tab.addTabListener(this);
        tab.setHeight(getHeight());
        sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
        tabs.put(tab, scene);
    }

    public void updateScene(final IGetScene scene) {

        String name = getPaddedSceneName(scene);
        tabs.getKey(scene).setText(name);
    }

    private String getPaddedSceneName(final IGetScene scene) {
        String name = scene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < TAB_MINIMUM_TEXT_LENGTH) {
            name += " ";
        }
        return name;
    }

    public void clearSceneTabs() {
        if (tabs != null) {
            tabs.clear();
            sceneButtonGroup.clearChildren();
            sceneButtonGroup.addActor(tabUtilityButtons);
        }
        if (hiddenSceneList != null) {
            hiddenSceneList.getItems().clear();
        }
    }

    @Override
    public void tabEvent(
            final Tab source,
            final io.github.visualista.visualista.view.TabListener.EventType type) {
        if (type == EventType.SELECT) {
            if (activeScene == tabs.getValue(source)) {
                source.makeNameEditable();
                source.giveFocusFrom(getStage());
            } else {
                eventManager.selectScene(tabs.getValue(source));
            }
        } else if (type == EventType.NAME_CHANGE) {
            eventManager.changeSceneName(tabs.getValue(source),
                    source.newName());
        }

    }

    private HorizontalGroup createUpperBorderContent() {

        // Declaring and defining local variables //
        final HorizontalGroup upperBorderContentWrapper = new HorizontalGroup();
        hiddenSceneList = createHiddenSceneList();
        final ScrollPane hiddenSceneDropDown = createHiddenSceneDropDown(hiddenSceneList);
        final TextButton newSceneButton = createNewSceneButton();
        final TextButton overflowDropdownButton = createSceneOverflowButton(hiddenSceneDropDown);
        tabUtilityButtons = createTabUtilityButtons(newSceneButton,
                overflowDropdownButton);
        sceneButtonGroup = new HorizontalGroup();
        // End defining local variables //

        sceneButtonGroup.addActor(tabUtilityButtons);

        upperBorderContentWrapper.addActor(sceneButtonGroup);

        upperBorderContentWrapper.addActor(hiddenSceneDropDown);
        return upperBorderContentWrapper;
    }

    private TextButton createNewSceneButton() {
        TextButton newButton = new TextButton("+", uiSkin);
        newButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                eventManager.newScene();
            }
        });
        return newButton;
    }

    private TextButton createSceneOverflowButton(final ScrollPane dropDownScenes) {
        TextButton newButton = new TextButton(">", uiSkin);
        newButton.setVisible(false);
        newButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                dropDownScenes.setVisible(true);
            }
        });
        return newButton;
    }

    private HorizontalGroup createTabUtilityButtons(
            final TextButton... utilityButtons) {
        HorizontalGroup newGroup = new HorizontalGroup();
        for (TextButton button : utilityButtons) {
            newGroup.addActor(button);
        }

        return newGroup;

    }

    private ScrollPane createHiddenSceneDropDown(final List<Tab> hiddenScenes) {
        newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
        newScrollPane = new ScrollPane(hiddenScenes, uiSkin);
        newScrollPane.setFadeScrollBars(false);

        newScrollPane.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    final float y) {
                // Debug Code //
                Gdx.app.log("Hidden Scenes Dropdown", "Clicked");
                // End Debug //
            }
        });
        newScrollPane.setVisible(false);
        return newScrollPane;
    }

    private void fixOverFlowingScenes() {
        throw new UnsupportedOperationException("Not implemented yet");
        //addHiddenScenesToTabs();
        //removeOverflowingTabs();
    }

    private void removeOverflowingTabs() {
        int currentWidthOfTabs = calculateTotalTabWidth();
        SnapshotArray<Actor> children = sceneButtonGroup.getChildren();
        while (currentWidthOfTabs > getWidth()
                && children.size > 1 && getWidth() > 0) {
            Actor tabToRemove = children.get(0);
            if (tabToRemove instanceof Tab) {
                currentWidthOfTabs-=tabToRemove.getWidth();
                sceneButtonGroup.removeActor(tabToRemove);
                hiddenSceneList.getItems().add((Tab) tabToRemove);
            }
        }

    }

    private int calculateTotalTabWidth() {
        int totalWidth = 0;
        for (Actor actor : sceneButtonGroup.getChildren()) {
            totalWidth += actor.getWidth();
        }
        return totalWidth;
    }

    private void addHiddenScenesToTabs() {
        for (Tab tab : hiddenSceneList.getItems()) {
            sceneButtonGroup.addActor(tab);
        }
    }

    private List<Tab> createHiddenSceneList() {
        List<Tab> newList = new List<Tab>(uiSkin);
        newList.setColor(Color.BLACK);
        return newList;
    }

    public void resize() {
        if (getStage() != null) {
            setSize(UpperBorder.UPPER_BORDER_WIDTH_RATIO
                    * getStage().getWidth(),
                    UpperBorder.UPPER_BORDER_HEIGHT_RATIO
                    * getStage().getHeight());
            setPosition(UpperBorder.UPPER_BORDER_X_DISPLACEMENT_RATIO
                    * getStage().getWidth(),
                    UpperBorder.UPPER_BORDER_Y_DISPLACEMENT_RATIO
                    * getStage().getHeight());
            setLineSize(UpperBorder.UPPER_BORDER_LINE_SIZE);
            setColor(UpperBorder.UPPER_BORDER_COLOR);
            // Makes sure the border lines does not collide!
            sceneButtonGroup.setX(getLineSize());
            newScrollPane.setSize(getStage().getWidth()
                    * HIDDEN_SCENE_WIDTH_RATIO, getStage().getHeight()
                    * HIDDEN_SCENE_HEIGHT_RATIO);
            newScrollPane.setPosition(getStage().getWidth()
                    * UpperBorder.HIDDEN_SCENE_X_DISPLACEMENT_RATIO, getStage()
                    .getHeight()
                    * UpperBorder.HIDDEN_SCENE_Y_DISPLACEMENT_RATIO);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
