package io.github.visualista.visualista.model;

import java.util.Iterator;

import io.github.visualista.visualista.editorcontroller.*;
import io.github.visualista.visualista.playercontroller.ActionEvent;
import io.github.visualista.visualista.playercontroller.ActionEventListener;
import io.github.visualista.visualista.playercontroller.ActionEventSource;
import io.github.visualista.visualista.playercontroller.ActionEventType;

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
