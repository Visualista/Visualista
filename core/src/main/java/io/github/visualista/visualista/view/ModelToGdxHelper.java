package io.github.visualista.visualista.view;

import java.io.File;

import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.model.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum ModelToGdxHelper {
    ;

    public static Image createImageFor(IGetTile tile) {

        return new Image(createDrawableFor(tile));
    }

    public static Image createImageFor(IGetActor actor) {
        return new Image(createDrawableFor(actor));
    }

    public static Image createImageFor(IGetScene scene) {
        return new Image(createDrawableFor(scene));
    }

    public static TextureRegionDrawable createDrawableFor(IGetScene scene) {
        if (scene == null) {
            return null;
        }
        return createDrawableFor(scene.getImage());
    }

    public static TextureRegionDrawable createDrawableFor(IGetTile tile) {
        if (tile == null) {
            return null;
        }
        return createDrawableFor(tile.getActor());
    }

    public static TextureRegionDrawable createDrawableFor(IGetActor actor) {
        if (actor == null) {
            return null;
        }
        return createDrawableFor(actor.getImage());
    }

    public static TextureRegionDrawable createDrawableFor(
            io.github.visualista.visualista.model.Image image) {
        if (image == null) {
            return null;
        }
        return createDrawableFor(image.getFileHandle());
    }

    public static TextureRegionDrawable createDrawableFor(FileHandle fileHandle) {
        if (fileHandle == null) {
            return null;
        }
        return new TextureRegionDrawable(new TextureRegion(new Texture(
                fileHandle)));
    }

}
