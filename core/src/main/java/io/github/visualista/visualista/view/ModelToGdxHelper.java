package io.github.visualista.visualista.view;

import java.io.File;

import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetTile;
import io.github.visualista.visualista.model.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum ModelToGdxHelper {
    ;

    public static Image createImageFor(IGetTile tileAtCurrentPosition) {
        IGetActor tileActor = tileAtCurrentPosition.getActor();
        if (tileActor.hasNoImage()) {
            return new Image((TextureRegionDrawable)null);
        }
        File javaFile = tileActor.getImage().getFile();
        FileHandle fileFromModel = Gdx.files.absolute(javaFile
                .getAbsolutePath());
        return new Image(new TextureRegionDrawable(new TextureRegion(
                new Texture(fileFromModel))));
    }
}
