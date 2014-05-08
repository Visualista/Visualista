package io.github.visualista.visualista.core.model;

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
    public void callAction() {
        // TODO Auto-generated method stub

    }

}
