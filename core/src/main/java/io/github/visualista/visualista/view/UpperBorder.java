package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.util.BiDiMap;
import io.github.visualista.visualista.view.TabListener.EventType;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;

class UpperBorder extends Border implements Updateable, TabListener {

    /**
     * 
     */
    private final EditorView upperBorder;

    private List<Tab> hiddenSceneList;

    private BiDiMap<Tab, IGetScene> tabs;

    private HorizontalGroup sceneButtonGroup;

    private HorizontalGroup tabUtilityButtons;

    private Tab editingTab;

    // Declaring static variables //
    private static final float HIDDEN_SCENE_X_DISPLACEMENT_RATIO = 0.7f;

    private static final float HIDDEN_SCENE_Y_DISPLACEMENT_RATIO = 0.7f;

    private static final float HIDDEN_SCENE_HEIGHT_RATIO = 0.2f;

    private static final int UPPER_BORDER_LINE_SIZE = 1;

    private static final Color UPPER_BORDER_COLOR = Color.BLACK;

    private static final float UPPER_BORDER_Y_DISPLACEMENT_RATIO = 0.95f;

    private static final float UPPER_BORDER_X_DISPLACEMENT_RATIO = 0.25f;

    private static final float UPPER_BORDER_WIDTH_RATIO = 0.5f;

    private static final float UPPER_BORDER_HEIGHT_RATIO = 0.05f;

    public UpperBorder(EditorView editorView, Stage stage) {
        upperBorder = editorView;
        tabs = new BiDiMap<Tab, IGetScene>();
        this.setActor(createUpperBorderContent());
        resizeUpperBorder();
        stage.addActor(this);
    }

    public void changeActiveScene(IGetScene scene) {
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

    private void bringTabToTabGroup(Tab tab) {
        int index = hiddenSceneList.getItems().indexOf(tab, true);
        if (index != -1) {
            hiddenSceneList.getItems().removeIndex(index);
            sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
        }

    }

    public void addNewScene(IGetScene scene) {
        String name = getPaddedSceneName(scene);
        Tab tab = new Tab(name, upperBorder.uiSkin);
        tab.addTabListener(this);
        tab.setHeight(upperBorder.upperBorder.getHeight());
        sceneButtonGroup.addActorBefore(tabUtilityButtons, tab);
        tabs.put(tab, scene);

    }

    public void updateScene(IGetScene scene) {

        String name = getPaddedSceneName(scene);
        tabs.getKey(scene).setText(name);
    }

    private String getPaddedSceneName(IGetScene scene) {
        String name = scene.getName();
        if (name == null) {
            name = "";
        }
        while (name.length() < 5) {
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
    public void tabEvent(Tab source,
            io.github.visualista.visualista.view.TabListener.EventType type) {
        if (type == EventType.SELECT) {
            if (upperBorder.activeScene == tabs.getValue(source)) {
                source.makeNameEditable();
                source.giveFocusFrom(upperBorder.stage);
                editingTab = source;
            } else {
                upperBorder.eventManager.fireViewEvent(this, Type.SELECT_SCENE,
                        tabs.getValue(source));
            }
        } else if (type == EventType.NAME_CHANGE) {
            upperBorder.eventManager.fireViewEvent(this, Type.CHANGE_SCENE_NAME,
                    tabs.getValue(source), source.newName());
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
        sceneButtonGroup.setX(this.getLineSize()); // Makes sure the border
                                                   // lines does not
                                                   // collide!

        upperBorderContentWrapper.addActor(hiddenSceneDropDown);
        return upperBorderContentWrapper;
    }

    private TextButton createNewSceneButton() {
        TextButton newButton = new TextButton("+", upperBorder.uiSkin);
        newButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    float y) {
                UpperBorder.this.upperBorder.eventManager.fireViewEvent(this, Type.NEW_SCENE);
            }
        });
        return newButton;
    }

    private TextButton createSceneOverflowButton(
            final ScrollPane dropDownScenes) {
        TextButton newButton = new TextButton(">", upperBorder.uiSkin);
        newButton.setVisible(false);
        newButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    float y) {
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

    private ScrollPane createHiddenSceneDropDown(List<Tab> hiddenScenes) {
        ScrollPane newScrollPane = new ScrollPane(hiddenScenes, upperBorder.uiSkin);
        newScrollPane = new ScrollPane(hiddenScenes, upperBorder.uiSkin);
        newScrollPane.setFadeScrollBars(false);
        newScrollPane.setWidth(100);// actionList.getWidth());
        newScrollPane.setPosition(upperBorder.stage.getWidth()
                * UpperBorder.HIDDEN_SCENE_X_DISPLACEMENT_RATIO, upperBorder.stage.getHeight()
                * UpperBorder.HIDDEN_SCENE_Y_DISPLACEMENT_RATIO);

        newScrollPane.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x,
                    float y) {
                // Debug Code //
                Gdx.app.log("Hidden Scenes Dropdown", "Clicked");
                // End Debug //
            }
        });
        newScrollPane.setVisible(false);
        return newScrollPane;
    }

    private ArrayList<Tab> fixOverFlowingScenes(
            HorizontalGroup sceneButtonGroup,
            List<Tab> currentlyHiddenScenes) {
        ArrayList<Tab> newTablist = new ArrayList<Tab>();
        ArrayList<Tab> removedActors = new ArrayList<Tab>();
        for (Actor tab : currentlyHiddenScenes.getItems()) {
            sceneButtonGroup.addActor(tab);
        }
        SnapshotArray<Actor> children = sceneButtonGroup.getChildren();
        if (children.size <= 0) {
            return newTablist;
        }

        float currentWidthOfTabs = 0;
        Tab tabToRemove = (Tab) children.get(children.size - 1);

        Iterator<Actor> it = children.iterator();
        while (it.hasNext()) {
            currentWidthOfTabs += it.next().getWidth();
        }

        while (currentWidthOfTabs > sceneButtonGroup.getWidth()
                && children.size > 1) {
            sceneButtonGroup.removeActor(tabToRemove);
            removedActors.add(tabToRemove);
            tabToRemove = (Tab) children.get(children.size - 1);
        }
        currentlyHiddenScenes.setItems((Tab[]) removedActors.toArray());

        return newTablist;

    }

    private List<Tab> createHiddenSceneList() {
        List<Tab> newList = new List<Tab>(upperBorder.uiSkin);
        newList.setWidth(150);
        newList.setColor(Color.BLACK);
        return newList;
    }

    private void resizeUpperBorder() {
        setSize(UpperBorder.UPPER_BORDER_WIDTH_RATIO * upperBorder.stage.getWidth(),
                UpperBorder.UPPER_BORDER_HEIGHT_RATIO * upperBorder.stage.getHeight());
        setPosition(UpperBorder.UPPER_BORDER_X_DISPLACEMENT_RATIO * upperBorder.stage.getWidth(),
                UpperBorder.UPPER_BORDER_Y_DISPLACEMENT_RATIO * upperBorder.stage.getHeight());
        setLineSize(UpperBorder.UPPER_BORDER_LINE_SIZE);
        setColor(UpperBorder.UPPER_BORDER_COLOR);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}