package io.github.visualista.visualista.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.visualista.visualista.controller.ActionEvent;
import io.github.visualista.visualista.controller.ActionEventListener;
import io.github.visualista.visualista.controller.ActionEventSource;
import io.github.visualista.visualista.controller.ActionEventType;

public class SetSceneAction extends ActionEventSource implements IPlayAction {

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
    public void callAction() {
       fireActionEvent();

    }

    @Override
    public void fireActionEvent() {
        final ActionEvent ae = new ActionEvent(this, ActionEventType.SET_SCENE, targetScene);
        final Iterator<ActionEventListener> it = actorEventListeners.iterator();
        while (it.hasNext()) {
            it.next().handleActionEvent(ae);
        }
        
    }

}
