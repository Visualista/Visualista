package io.github.visualista.visualista.core.model;

public class SetSceneTextAction implements Action{

	private String sceneText;
	
	public SetSceneTextAction(String sceneText){
		this.sceneText = sceneText;
	}
	
	public void callAction() {
		// TODO Auto-generated method stub
		
	}
	
	public void setSceneText(String sceneText){
		this.sceneText = sceneText;
	}
	
	public String getSceneText(){
		return sceneText;
	}

}
