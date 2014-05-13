package io.github.visualista.visualista.model;

import java.util.Iterator;

import io.github.visualista.visualista.controller.ActionEvent;
import io.github.visualista.visualista.controller.ActionEventListener;
import io.github.visualista.visualista.controller.ActionEventSource;
import io.github.visualista.visualista.controller.ActionEventType;
import io.github.visualista.visualista.util.Point;

public class ReplaceTileAction extends ActionEventSource implements IPlayAction {

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
        fireActionEvent();

    }

    @Override
    public void fireActionEvent() {
        //TODO Make this work
        final ActionEvent ae = new ActionEvent(this, ActionEventType.SET_SCENE, "Test");
        final Iterator<ActionEventListener> it = actorEventListeners.iterator();
        while (it.hasNext()) {
            it.next().handleActionEvent(ae);
        }
        
    }
}
