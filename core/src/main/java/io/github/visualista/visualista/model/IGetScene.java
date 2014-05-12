package io.github.visualista.visualista.model;

import java.util.List;

public interface IGetScene {
    public String getStoryText();
    
    public String getName();
    
    public List<IGetActor> getActorsInScene();
    
    public Grid getGrid();

}
