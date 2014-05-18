package io.github.visualista.visualista.view;

import java.io.File;

import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
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

    public static TextureRegionDrawable createDrawableFor(IGetTile tile) {
        return createDrawableFor(tile.getActor());
    }
    
    public static TextureRegionDrawable createDrawableFor(IGetActor actor) {
        if (actor.hasNoImage()) {
            return (TextureRegionDrawable) null;
        }
        File javaFile = actor.getImage().getFile();
        FileHandle fileFromModel = Gdx.files.absolute(javaFile
                .getAbsolutePath());
        return new TextureRegionDrawable(new TextureRegion(new Texture(
                fileFromModel)));
    }

}
