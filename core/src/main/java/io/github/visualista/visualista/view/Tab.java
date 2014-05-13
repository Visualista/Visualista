package io.github.visualista.visualista.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Tab extends HorizontalGroup {
    private static final float FONT_SCALE_RATIO = 1/ 30.0f;
    private static final float CLOSE_IMAGE_RATIO = 0.8f;
    final static Drawable closeDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture(Gdx.files.internal("icons/tile.png"))));
    final Label label;
    final Image closeImage;
    private Border closeImageBorder;

    public Tab(String name, Skin uiSkin) {
        label = new Label(name, uiSkin);
        closeImage = new Image(closeDrawable);
        closeImageBorder = new Border();
        closeImageBorder.setLineSize(1);
        closeImageBorder.setLineOutsideActor(true);
        closeImageBorder.setActor(closeImage);
        closeImage.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
            }
        });
        this.addActor(label);
        this.addActor(closeImageBorder);

    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
    }

    @Override
    public void setHeight(float height) {
        label.setFontScale((height *FONT_SCALE_RATIO));
        closeImageBorder.setSize(height*CLOSE_IMAGE_RATIO, height*CLOSE_IMAGE_RATIO);
        super.setHeight(height);
    }

    public CharSequence getText() {
        return label.getText();
    }

}
