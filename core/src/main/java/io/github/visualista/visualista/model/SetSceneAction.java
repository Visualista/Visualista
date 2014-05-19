package io.github.visualista.visualista.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.visualista.visualista.playercontroller.ActionEvent;
import io.github.visualista.visualista.playercontroller.ActionEventListener;
import io.github.visualista.visualista.playercontroller.ActionEventSource;
import io.github.visualista.visualista.playercontroller.ActionEventType;

public class SetSceneAction implements IPlayAction {

    private Scene targetScene;

    public SetSceneAction(Scene targetScene) {
        this.targetScene = targetScene;
    }

    public Scene getTargetScene() {
        return targetScene;
    }

    public void setTargetScene(Scene targetScene) {
        this.targetScene = targetScene;
    }

    public String getExplainatoryName() {
        return "SwitchScene(\"" + targetScene.getName() + "\")";
    }

    @Override
    public Object getActionData() {
       return targetScene;

    }

}
