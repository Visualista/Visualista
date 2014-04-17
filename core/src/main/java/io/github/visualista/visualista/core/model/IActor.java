package io.github.visualista.visualista.core.model;

import java.util.List;

public interface IActor {
	
	public String getName();
	
	public void setName(String newName);
	
	public Image getImage();
	
	public void setImage(Image newImage);
	
	public List<IEditAction> getActions();
}
