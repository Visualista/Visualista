package io.github.visualista.visualista.model;

import java.util.Iterator;

import io.github.visualista.visualista.controller.*;

public class SetSceneTextAction extends ActionEventSource implements IPlayAction {

    private String sceneText;

    public void setSceneText(String sceneText) {
        this.sceneText = sceneText;
    }

    public String getSceneText() {
        return sceneText;
    }

    public String getExplainatoryName() {
        return "SetText(\"" + sceneText + "\")";
    }

    @Override
    public void callAction() {
        fireActionEvent();

    }

    @Override
    public void fireActionEvent() {
        final ActionEvent ae = new ActionEvent(this, ActionEventType.SET_TEXT, sceneText);
        final Iterator<ActionEventListener> it = actorEventListeners.iterator();
        while (it.hasNext()) {
            it.next().handleActionEvent(ae);
        }
    }

}
