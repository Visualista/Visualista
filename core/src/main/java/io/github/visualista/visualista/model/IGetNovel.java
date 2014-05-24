package io.github.visualista.visualista.model;

import java.util.List;

public interface IGetNovel {
    String getName();

    int getSceneCount();

    List<Scene> getScenes();

    Scene getCurrentScene();
}
