package io.github.visualista.visualista.controller;

import io.github.visualista.visualista.controller.ViewEventListener;
import io.github.visualista.visualista.model.IGetActor;
import io.github.visualista.visualista.model.IGetNovel;
import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.model.Scene;

public interface IVisualistaView {
    void addScene(IGetScene newScene);

    void removeViewEventListener(ViewEventListener eventListener);

    void addViewEventListener(ViewEventListener eventListener);

    void updateScene(IGetScene currentScene);

    void removeScene(IGetScene scene);

    void changeActiveScene(IGetScene scene);

    void changeActiveNovel(IGetNovel updatedNovel);

    boolean getIsReady();

    void clearModel();

    void selectActor(IGetActor targetObject);

    void updateActor(IGetActor updatedActor);

    

}
