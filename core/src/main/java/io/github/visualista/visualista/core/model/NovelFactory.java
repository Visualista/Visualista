package io.github.visualista.visualista.core.model;

import java.util.*;

public enum NovelFactory {
    ;
    private static IdGenerator id = new IdGenerator();

    public static Novel createNovel() {
        Map<Integer, Scene> sceneMap = new HashMap<Integer, Scene>();
        Scene scene = new Scene();
        sceneMap.put(id.generateId(), scene);
        return new Novel(sceneMap);
    }
}
