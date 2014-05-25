package io.github.visualista.visualista.model;

public class SetSceneAction implements IAction {

    private Scene targetScene;

    public SetSceneAction(final Scene targetScene) {
        this.targetScene = targetScene;
    }

    public Scene getTargetScene() {
        return targetScene;
    }

    public void setTargetScene(final Scene targetScene) {
        this.targetScene = targetScene;
    }

    public String getExplainatoryName() {
        return "Switch to the " + targetScene.getName() + " Scene";
    }

    @Override
    public String toString() {
        return getExplainatoryName();
    }

    @Override
    public Object getActionData() {
        return targetScene;

    }

}
