package io.github.visualista.visualista.core.model;

public class SwitchSceneAction implements IAction{

	private Scene targetScene;
	
	public SwitchSceneAction(Scene targetScene) {
		this.targetScene = targetScene;
	}
	
	public void callAction(){
		
	}
	
	public Scene getTargetScene(){
		return targetScene;
	}
	
	public void setTargetScene(Scene targetScene){
		this.targetScene = targetScene;
	}
	

}
