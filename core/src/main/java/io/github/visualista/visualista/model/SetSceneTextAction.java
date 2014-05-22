package io.github.visualista.visualista.model;

public class SetSceneTextAction implements IAction {

    private String sceneText;

    public SetSceneTextAction(String sceneText) {
        super();
        this.sceneText = sceneText;
    }

    public void setSceneText(String sceneText) {
        this.sceneText = sceneText;
    }

    public String getSceneText() {
        return sceneText;
    }

    public String getExplainatoryName() {
        return "Change Scene text to \"" + sceneText + "\"";
    }

    @Override
    public Object getActionData() {
        return sceneText;

    }

}
