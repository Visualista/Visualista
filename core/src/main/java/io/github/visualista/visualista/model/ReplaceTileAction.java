package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Point;

public class ReplaceTileAction implements IPlayAction {

    private Point targetTile;
    private Actor replacementActor;

    public ReplaceTileAction(Point targetTile, Actor replacementActor) {
        this.targetTile = targetTile;
        this.replacementActor = replacementActor;
    }

    public Point getTargetTile() {
        return targetTile;
    }

    public Actor getReplacementActor() {
        return replacementActor;
    }

    public void setTargetTile(Point targetTile) {
        this.targetTile = targetTile;
    }

    public void setReplacementActor(Actor replacementActor) {
        this.replacementActor = replacementActor;
    }

    public String toString() {
        return "SetTile(" + replacementActor.toString() + " | "
                + targetTile.getX() + "," + targetTile.getY() + ")";
    }

    public String getExplainatoryName() {
        return "SetTile(" + replacementActor.toString() + " | "
                + targetTile.getX() + "," + targetTile.getY() + ")";
    }

    @Override
    public void callAction() {
        // TODO Auto-generated method stub

    }
}
