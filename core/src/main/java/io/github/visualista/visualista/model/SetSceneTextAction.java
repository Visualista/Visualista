package io.github.visualista.visualista.model;

public class SetSceneTextAction implements IAction {

    private static final int SCENE_TEXT_MAX_LENGTH_BEFORE_OVERFLOW = 5;
    private String sceneText;

    public SetSceneTextAction(final String sceneText) {
        super();
        this.sceneText = sceneText;
    }

    public void setSceneText(final String sceneText) {
        this.sceneText = sceneText;
    }

    public String getSceneText() {
        return sceneText;
    }

    public String getExplainatoryName() {
        if (sceneText.length() > SCENE_TEXT_MAX_LENGTH_BEFORE_OVERFLOW){
            return "Change Scene text to\"" + sceneText.substring(0, SCENE_TEXT_MAX_LENGTH_BEFORE_OVERFLOW) + "...\"";
        }
        return "Change Scene text to \"" + sceneText + "\"";
    }

    @Override
    public String toString(){
        return getExplainatoryName();
    }

    @Override
    public Object getActionData() {
        return sceneText;

    }

}
