package io.github.visualista.visualista.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Tab extends Stack {
    private static final float FONT_SCALE_RATIO = 1 / 30.0f;
    private static final float CLOSE_IMAGE_RATIO = 0.8f;
    final static Drawable closeDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture(Gdx.files.internal("icons/tile.png"))));
    private final Label label;
    private final Image closeImage;
    private final Border closeImageBorder;
    private final HorizontalGroup hGroup;
    private final Rectangle background;
    ArrayList<TabClickListener> clickListeners = new ArrayList<TabClickListener>();
    private TextField labelTextField;

    public Tab(String name, Skin uiSkin) {
        label = new Label(name, uiSkin);
        closeImage = new Image(closeDrawable);
        closeImageBorder = new Border();
        hGroup = new HorizontalGroup();
        background = new Rectangle(Color.GRAY);
        closeImageBorder.setLineSize(1);
        closeImageBorder.setLineOutsideActor(true);
        closeImageBorder.setActor(closeImage);
        closeImage.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                for (TabClickListener listener : clickListeners) {
                    listener.tabEvent(Tab.this, Type.CLOSE);
                }
            }
        });

        labelTextField = new TextField(name, uiSkin);

        Rectangle padding1 = new Rectangle(new Color(0, 0, 0, 0));
        padding1.setWidth(5);
        hGroup.addActor(padding1);
        hGroup.addActor(label);
        Rectangle padding2 = new Rectangle(new Color(0, 0, 0, 0));
        padding2.setWidth(5);
        hGroup.addActor(padding2);

        hGroup.addActor(closeImageBorder);
        this.add(background);
        this.add(hGroup);
        ClickListener selectListener = new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, float y) {
                for (TabClickListener listener : clickListeners) {
                    Gdx.app.log("Tab", "Select");
                    listener.tabEvent(Tab.this, Type.SELECT);
                }
            }
        };
        padding1.addCaptureListener(selectListener);
        label.addCaptureListener(selectListener);
        padding2.addCaptureListener(selectListener);

    }

    @Override
    protected void sizeChanged() {
        if (background != null) {
            background.setSize(hGroup.getHeight(), hGroup.getWidth());
        }
        super.sizeChanged();
    }

    @Override
    public void setHeight(float height) {
        if (label != null && closeImageBorder != null) {
            label.setFontScale((height * FONT_SCALE_RATIO));
            
            closeImageBorder.setSize(height * CLOSE_IMAGE_RATIO, height
                    * CLOSE_IMAGE_RATIO);
        }
        super.setHeight(height);
    }

    public CharSequence getText() {
        return label.getText();
    }

    public void addClickListener(TabClickListener listener) {
        clickListeners.add(listener);
    }

    public enum Type {
        SELECT, CLOSE
    }

    public void makeNameEditable() {
        hGroup.addActorBefore(label, labelTextField);
        hGroup.removeActor(label);
    }
    
    public void useSelectStyle(boolean isTabSelected){
        if(isTabSelected){
            background.setColor(Color.LIGHT_GRAY);
        }else{
            background.setColor(Color.GRAY);
        }
        
    }

    public boolean nameWasChanged() {
        return !labelTextField.getText().equals(label.getText());
    }

    public String newName() {
        return labelTextField.getText().toString();
    }

    public TextField getTextField() {
        // TODO: Remove this method. It should not be used except now when it is
        // temporary needed
        return labelTextField;
    }

    public void stopEditing() {
        hGroup.addActorBefore(labelTextField,label);
        hGroup.removeActor(labelTextField);
        
    }
}
