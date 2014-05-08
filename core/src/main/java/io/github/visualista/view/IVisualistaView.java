package io.github.visualista.view;

import io.github.visualista.visualista.controller.ViewEventListener;
import io.github.visualista.visualista.core.model.IGetScene;

public interface IVisualistaView {
    public void addScene(IGetScene newScene);
    
    public void removeViewEventListener(ViewEventListener eventListener);
    
    public void addViewEventListener(ViewEventListener eventListener);
    
}
