package io.github.visualista.visualista.core.model;

import io.github.visualista.visualista.util.Point;

public interface IEditReplaceTileAction extends IEditAction {

	public void setReplacementActor(Actor replacementActor);

	public Actor getReplacementActor();

	public void setTargetTile(Point targetTile);

	public Point getTargetTile();

}
