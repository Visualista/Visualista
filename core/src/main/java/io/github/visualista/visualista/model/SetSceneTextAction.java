package io.github.visualista.visualista.model;

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
