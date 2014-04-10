package io.github.visualista.visualista.core.model;

public interface IEditSwitchSceneAction extends IEditAction{

	public void setTargetScene(Scene targetScene);
	
	public Scene getTargetScene();
}
