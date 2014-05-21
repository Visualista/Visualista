package io.github.visualista.visualista.model;

public class SetSceneAction implements IAction {

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
