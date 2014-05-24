package io.github.visualista.visualista.view;

import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum ModelToGdxHelper {
    ;

    public static Image createImageFor(final IGetTile tile) {

        return new Image(createDrawableFor(tile));
    }

    public static Image createImageFor(final IGetActor actor) {
        return new Image(createDrawableFor(actor));
    }

    public static Image createImageFor(final IGetScene scene) {
        return new Image(createDrawableFor(scene));
    }

    public static TextureRegionDrawable createDrawableFor(final IGetScene scene) {
        if (scene == null) {
            return null;
        }
        return createDrawableFor(scene.getImage());
    }

    public static TextureRegionDrawable createDrawableFor(final IGetTile tile) {
        if (tile == null) {
            return null;
        }
        return createDrawableFor(tile.getActor());
    }

    public static TextureRegionDrawable createDrawableFor(final IGetActor actor) {
        if (actor == null) {
            return null;
        }
        return createDrawableFor(actor.getImage());
    }

    public static TextureRegionDrawable createDrawableFor(
            final io.github.visualista.visualista.model.Image image) {
        if (image == null) {
            return null;
        }
        return createDrawableFor(image.getFileHandle());
    }

    public static TextureRegionDrawable createDrawableFor(final FileHandle fileHandle) {
        if (fileHandle == null) {
            return null;
        }
        return new TextureRegionDrawable(new TextureRegion(new Texture(
                fileHandle)));
    }

}
