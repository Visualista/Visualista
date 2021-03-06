package io.github.visualista.visualista.view;

import io.github.visualista.visualista.view.TabListener.EventType;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Tab extends Stack {
    private static final int PADDING_SIZE = 5;
    private static final float FONT_SCALE_RATIO = 0.033f;
    private static final float CLOSE_IMAGE_RATIO = 0.8f;
    private static final Drawable CLOSE_DRAWABLE = new TextureRegionDrawable(
            new TextureRegion(new Texture(Gdx.files.internal("icons/tile.png"))));
    private final Label label;
    private final Image closeImage;
    private final Border closeImageBorder;
    private final HorizontalGroup hGroup;
    private final Rectangle background;
    private final java.util.List<TabListener> tabListeners = new ArrayList<TabListener>();
    private final TextField labelTextField;

    public Tab(final String name, final Skin uiSkin) {
        label = new Label(name, uiSkin);
        closeImage = new Image(CLOSE_DRAWABLE);
        closeImageBorder = new Border();
        hGroup = new HorizontalGroup();
        background = new Rectangle(Color.GRAY);
        closeImageBorder.setLineSize(1);
        closeImageBorder.setLineOutsideActor(true);
        closeImageBorder.setActor(closeImage);
        closeImage.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                for (TabListener listener : tabListeners) {
                    listener.tabEvent(Tab.this, EventType.CLOSE);
                }
            }
        });

        labelTextField = new TextField(name, uiSkin);
        labelTextField.addCaptureListener(new FocusListener(){

            @Override
            public void keyboardFocusChanged(final FocusEvent event, final Actor actor,
                    final boolean focused) {
                if(!focused){
                    stopEditing();
                }
                super.keyboardFocusChanged(event, actor, focused);
            }

        });

        Rectangle padding1 = new Rectangle(new Color(0, 0, 0, 0));
        padding1.setWidth(PADDING_SIZE);
        hGroup.addActor(padding1);
        hGroup.addActor(label);
        Rectangle padding2 = new Rectangle(new Color(0, 0, 0, 0));
        padding2.setWidth(PADDING_SIZE);
        hGroup.addActor(padding2);

        hGroup.addActor(closeImageBorder);
        add(background);
        add(hGroup);
        ClickListener selectListener = new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                for (TabListener listener : tabListeners) {
                    listener.tabEvent(Tab.this, EventType.SELECT);
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
            background.setSize(hGroup.getWidth(), hGroup.getHeight());
        }
        super.sizeChanged();
    }

    @Override
    public void setHeight(final float height) {
        if (label != null && closeImageBorder != null) {
            label.setFontScale(height * FONT_SCALE_RATIO);

            closeImageBorder.setSize(height * CLOSE_IMAGE_RATIO, height
                    * CLOSE_IMAGE_RATIO);
        }
        super.setHeight(height);
    }

    public CharSequence getText() {
        return label.getText();
    }

    public void addTabListener(final TabListener listener) {
        tabListeners.add(listener);
    }



    public void makeNameEditable() {
        hGroup.addActorBefore(label, labelTextField);
        hGroup.removeActor(label);
    }

    public void useSelectStyle(final boolean isTabSelected){
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

    public void stopEditing() {
        if(nameWasChanged()){
            for(TabListener listener : tabListeners){
                listener.tabEvent(this, EventType.NAME_CHANGE);
            }
        }
        background.setSize(0,0);
        hGroup.addActorBefore(labelTextField,label);
        hGroup.removeActor(labelTextField);


    }

    public void setText(final String text){
        label.setText(text);
        labelTextField.setText(text);
    }

    public void giveFocusFrom(final Stage stage) {
        stage.setKeyboardFocus(labelTextField);
    }
}
