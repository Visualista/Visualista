package io.github.visualista.visualista.view;

import io.github.visualista.visualista.controller.ViewEventListener;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Scene;

public interface IVisualistaView {
    public void addScene(IGetScene newScene);
    
    public void removeViewEventListener(ViewEventListener eventListener);
    
    public void addViewEventListener(ViewEventListener eventListener);

    public void updateScene(IGetScene currentScene);
    
    public void removeScene(IGetScene scene);

    public void changeActiveScene(Scene scene);

    public void changeActiveNovel(IGetNovel updatedNovel);
    
}
