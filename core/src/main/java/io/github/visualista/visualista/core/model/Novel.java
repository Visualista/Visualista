package io.github.visualista.visualista.core.model;

import java.util.*;

public class Novel {
    private final Map<Integer,Scene> sceneMap;

    public Novel(Map<Integer,Scene> sceneMap) {
        this.sceneMap = sceneMap;
    }

    public int getSceneCount() {
        return sceneMap.size();
    }

    public Scene getSceneById(Integer i) {
        return sceneMap.get(i);
    }

}
