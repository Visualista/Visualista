package io.github.visualista.visualista.io;

import io.github.visualista.visualista.model.Actor;
import io.github.visualista.visualista.model.IAction;
import io.github.visualista.visualista.model.Novel;
import io.github.visualista.visualista.model.Scene;
import io.github.visualista.visualista.model.Tile;
import io.github.visualista.visualista.util.Matrix;
import io.github.visualista.visualista.util.Row;

import com.thoughtworks.xstream.XStream;

public class XStreamManager {

    private final XStream mainXStream;

    public XStreamManager() {
        mainXStream = new XStream();
        configureXStream();
    }

    private void configureXStream() {
        mainXStream.alias("Action", IAction.class);
        mainXStream.alias("Actor", Actor.class);
        mainXStream.alias("Scene", Scene.class);
        mainXStream.alias("Novel", Novel.class);
        mainXStream.alias("Tile", Tile.class);
        mainXStream.alias("Row", Row.class);
        mainXStream.addImplicitArray(Row.class, "row");
        mainXStream.addImplicitArray(Matrix.class, "matrix");
        mainXStream.setMode(XStream.ID_REFERENCES);

    }

    public XStream getMainXStream() {
        return mainXStream;
    }
}
