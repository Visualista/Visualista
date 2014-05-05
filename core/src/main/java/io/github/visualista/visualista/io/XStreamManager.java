package io.github.visualista.visualista.io;

import io.github.visualista.visualista.core.model.IEditAction;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Grid;
import io.github.visualista.visualista.core.model.Novel;
import io.github.visualista.visualista.core.model.Scene;
import io.github.visualista.visualista.core.model.Tile;
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
		mainXStream.alias("Action", IEditAction.class);
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
