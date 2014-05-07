package io.github.visualista.visualista.core.model;

import java.util.List;

public interface IEditScene {

    public String getStoryText();

    public void setStoryText(String storyText);

    public String getName();

    public void setName(String name);

    public Grid getGrid();

    public void addActor(Actor actor);

    public List<Actor> getActorsInScene();

}
