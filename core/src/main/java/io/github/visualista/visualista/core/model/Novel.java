package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Novel {
    private List<Scene> sceneList;

    public Novel(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public int getSceneCount() {
        return sceneList.size();
    }

}
