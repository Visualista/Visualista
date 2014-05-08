package io.github.visualista.view;

import io.github.visualista.visualista.controller.ViewEventListener;

public interface IVisualistaView {
    public void addScene();
    
    public void removeViewEventListener(ViewEventListener eventListener);
    
    public void addViewEventListener(ViewEventListener eventListener);
    
}
