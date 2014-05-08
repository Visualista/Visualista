package io.github.visualista.visualista.core.model;

import java.util.List;

public interface IGetScene {
    public String getStoryText();
    
    public String getName();
    
    public List<Actor> getActorsInScene();
    
    public Grid getGrid();

}
