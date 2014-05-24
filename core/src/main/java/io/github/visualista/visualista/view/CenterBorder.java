package io.github.visualista.visualista.view;

import io.github.visualista.visualista.editorcontroller.EditorViewEvent.Type;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetGrid;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.util.Dimension;
import io.github.visualista.visualista.util.IMatrixGet;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Point;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class CenterBorder extends Border implements Updateable {
    private static final int CENTER_BORDER_LINE_SIZE = 1;
    private static final Color CENTER_BORDER_COLOR = Color.BLACK;
    private static final float CENTER_BORDER_Y_DISPLACEMENT_RATIO = 0.2f;
    private static final float CENTER_BORDER_X_DISPLACEMENT_RATIO = 0.25f;
    private static final float CENTER_BORDER_HEIGHT_RATIO = 0.75f;
    private static final float CENTER_BORDER_WIDTH_RATIO = 0.5f;
    private final EditorView centralBorder;
    private IGetActor selectedActor;
    private final java.util.List<Border> borders = new ArrayList<Border>();
    private Dimension gridDimensions;
    private Image sceneBackgroundImage;


    // End static variables //
    public CenterBorder(final EditorView editorView) {
        centralBorder = editorView;
        resizeCenterBorder();
        createCenterEditorBorderContent();
        resizeCenterBorder();
    }

    public void updateScene(final IGetScene scene) {
        fillGridFromScene(scene);
        sceneBackgroundImage.setDrawable(ModelToGdxHelper
                .createDrawableFor(scene));
    }

    public void addNewScene(final IGetScene newScene) {
    }

    public void chaneActiveScene(final IGetScene scene) {
        fillGridFromScene(scene);
        sceneBackgroundImage.setDrawable(ModelToGdxHelper
                .createDrawableFor(scene));
    }

    public void selectActor(final IGetActor actor) {
        selectedActor = actor;

    }

    private void createCenterEditorBorderContent() {
        centralBorder.centerVerticalGroup = new VerticalGroup();

        sceneBackgroundImage = new Image();
        Stack stack = new Stack();
        stack.add(sceneBackgroundImage);

        centralBorder.centerVerticalGroupBorder = new Border();
        centralBorder.centerVerticalGroupBorder
        .setActor(centralBorder.centerVerticalGroup);
        stack.add(centralBorder.centerVerticalGroupBorder);

        setActor(stack);
    }

    private void fillGridFromScene(final IGetScene scene) {
        Matrix<Image> gridButtons = createImagesForGrid(scene);
        centralBorder.centerVerticalGroup.clearChildren();
        fillGrid(centralBorder.centerVerticalGroup, gridButtons);
        resizeCenterBorder();
    }

    private Matrix<Image> createImagesForGrid(final IGetScene scene) {
        Matrix<Image> gridButtons = new Matrix<Image>(scene.getIGetGrid()
                .getSize());
        IGetGrid grid = scene.getIGetGrid();
        int gridWidth = grid.getSize().getWidth();
        int gridHeight = grid.getSize().getHeight();

        for (int y = 0; y < gridHeight; ++y) {
            for (int x = 0; x < gridWidth; ++x) {
                final IGetTile tileAtCurrentPosition = grid.getAt(new Point(x,
                        y));
                final Image imageForCurrentTile = ModelToGdxHelper
                        .createImageFor(tileAtCurrentPosition);
                imageForCurrentTile.addCaptureListener(new ClickListener() {

                    @Override
                    public void clicked(final InputEvent event, final float x, final float y) {
                        if (selectedActor != null) {
                            if (centralBorder.selectedTool == EditorTool.ARROW) {

                                centralBorder.eventManager
                                .fireViewEvent(imageForCurrentTile,
                                        Type.TILE_SET_ACTOR,
                                        tileAtCurrentPosition,
                                        selectedActor);

                            } else if (centralBorder.selectedTool == EditorTool.CURSOR) {
                                centralBorder.eventManager
                                .fireViewEvent(this, Type.SELECT_TILE,
                                        tileAtCurrentPosition);
                            }
                        }
                        super.clicked(event, x, y);
                    }

                });
                gridButtons.setAt(new Point(x, y), imageForCurrentTile);
            }
        }
        return gridButtons;
    }

    /*
     * Old resizeCenterBorder private void resizeCenterBorder() {
     * centerBorder.setSize( horizontalDistanceBetween(leftBorder, rightBorder),
     * (upperBorder.getY() - lowerBorder.getY() - lowerBorder .getHeight()));
     * centerBorder.setPosition(rightSideOf(leftBorder), lowerBorder.getY() +
     * lowerBorder.getHeight()); centerVerticalGroupBorder.setLineSize(0);
     * centerVerticalGroupBorder.setSize(centerBorder.getWidth(),
     * centerBorder.getHeight());
     * 
     * }
     */

    public final void fillGrid(final VerticalGroup group, final IMatrixGet<Image> data) {
        float buttonLength = calculateBorderLength(group.getWidth(),
                group.getHeight(), data.getSize());
        gridDimensions = data.getSize();

        for (int i = 0; i < data.getSize().getHeight(); ++i) {
            HorizontalGroup row = new HorizontalGroup();
            group.addActor(row);
            for (int j = 0; j < data.getSize().getWidth(); ++j) {
                Image button = data.getAt(new Point(i, j));
                Border border = new Border();
                border.setActor(button);
                border.setSize(buttonLength, buttonLength);
                border.setLineSize(1);
                borders.add(border);
                row.addActor(border);
            }
        }
    }

    private float calculateBorderLength(final float width, final float height,
            final Dimension size) {
        float prefferedButtonHeight = height / size.getHeight();
        float prefferedButtonWidth = width / size.getWidth();
        float buttonLength = (float) Math.floor(Math.min(prefferedButtonWidth,
                prefferedButtonHeight));
        return buttonLength;
    }

    private void resizeCenterBorder() {
        setSize(CenterBorder.CENTER_BORDER_WIDTH_RATIO
                * centralBorder.stage.getWidth(),
                CenterBorder.CENTER_BORDER_HEIGHT_RATIO
                * centralBorder.stage.getHeight());
        setPosition(CenterBorder.CENTER_BORDER_X_DISPLACEMENT_RATIO
                * centralBorder.stage.getWidth(),
                CenterBorder.CENTER_BORDER_Y_DISPLACEMENT_RATIO
                * centralBorder.stage.getHeight());
        setLineSize(CenterBorder.CENTER_BORDER_LINE_SIZE);
        setColor(CenterBorder.CENTER_BORDER_COLOR);
        if (gridDimensions != null) {
            float buttonLength = calculateBorderLength(getWidth(), getHeight(),
                    gridDimensions);
            for (Border border : borders) {
                border.setSize(buttonLength, buttonLength);
            }
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
