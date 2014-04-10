package io.github.visualista.visualista.core.model;

public class SwitchSceneAction implements IEditSwitchSceneAction, IPlayAction{

	private Scene targetScene;
	
	public SwitchSceneAction(Scene targetScene) {
		this.targetScene = targetScene;
	}
	
	public Scene getTargetScene(){
		return targetScene;
	}
	
	public void setTargetScene(Scene targetScene){
		this.targetScene = targetScene;
	}

	@Override
	public String getExplainatoryName() {
		return "SwitchScene(\"" + targetScene.getName() + "\")";
	}

	@Override
	public void callAction() {
		// TODO Auto-generated method stub
		
	}
	

}
