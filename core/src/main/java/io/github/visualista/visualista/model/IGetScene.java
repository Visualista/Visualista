package io.github.visualista.visualista.model;

import java.util.List;

public interface IGetScene {
    String getStoryText();
    
    String getName();
    
    List<IGetActor> getActorsInScene();
    
    IGetGrid getIGetGrid();

    Image getImage();

}
