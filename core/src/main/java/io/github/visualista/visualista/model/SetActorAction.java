package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Point;

public class SetActorAction implements IAction {

    private Point targetTile;
    private Actor replacementActor;
    private final PositionedActor wrappedData;

    public SetActorAction(final Point targetTile, final Actor replacementActor) {
        this.targetTile = targetTile;
        this.replacementActor = replacementActor;
        wrappedData = new PositionedActor(targetTile, replacementActor);
    }

    public SetActorAction(final PositionedActor positionedActor) {
        this(positionedActor.getPosition(), positionedActor.getActor());
    }

    public Point getTargetTile() {
        return targetTile;
    }

    public Actor getReplacementActor() {
        return replacementActor;
    }

    public void setTargetTile(final Point targetTile) {
        this.targetTile = targetTile;
    }

    public void setReplacementActor(final Actor replacementActor) {
        this.replacementActor = replacementActor;
    }

    @Override
    public String toString() {
        return "Place " + replacementActor.getName() + " on tile  "
                + targetTile.getX() + "," + targetTile.getY();
    }


    public PositionedActor getWrappedData() {
        return wrappedData;
    }

    @Override
    public Object getActionData() {
        return wrappedData;
    }
}
