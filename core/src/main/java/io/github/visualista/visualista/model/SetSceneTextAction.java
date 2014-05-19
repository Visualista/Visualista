package io.github.visualista.visualista.model;

import java.util.Iterator;

import io.github.visualista.visualista.editorcontroller.*;
import io.github.visualista.visualista.playercontroller.ActionEvent;
import io.github.visualista.visualista.playercontroller.ActionEventListener;
import io.github.visualista.visualista.playercontroller.ActionEventSource;
import io.github.visualista.visualista.playercontroller.ActionEventType;

public class SetSceneTextAction implements IPlayAction {

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
    public Object getActionData() {
        return sceneText;

    }

}
