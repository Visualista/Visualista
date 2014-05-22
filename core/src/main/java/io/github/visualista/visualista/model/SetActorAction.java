package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Point;
import io.github.visualista.visualista.model.PositionedActor;

public class SetActorAction implements IAction {

    private Point targetTile;
    private Actor replacementActor;
    private PositionedActor wrappedData;

    public SetActorAction(Point targetTile, Actor replacementActor) {
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
        return "Place " + replacementActor.getName() + " on tile  "
                + targetTile.getX() + "," + targetTile.getY();
    }

    public String getExplainatoryName() {
        return "SetTile(" + replacementActor.toString() + " | "
                + targetTile.getX() + "," + targetTile.getY() + ")";
    }

    public PositionedActor getWrappedData(){
        return wrappedData;
    }
    
    private PositionedActor wrapData(){
        wrappedData = new PositionedActor(targetTile, replacementActor);
        return wrappedData;
    }
    
    @Override
    public Object getActionData() {
        return wrapData();
    }
}
