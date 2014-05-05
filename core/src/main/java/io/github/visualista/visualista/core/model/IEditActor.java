package io.github.visualista.visualista.core.model;

import java.util.List;

public interface IEditActor {

	public String getName();

	public void setName(String newName);

	public Image getImage();

	public void setImage(Image newImage);

	public List<IAction> getActions();
}
