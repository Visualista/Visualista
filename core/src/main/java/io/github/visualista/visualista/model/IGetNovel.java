package io.github.visualista.visualista.model;

import java.util.List;

public interface IGetNovel {
    public String getName();
    
    public int getSceneCount();
    
    public List<Scene> getScenes();
    
    public Scene getCurrentScene();
}
