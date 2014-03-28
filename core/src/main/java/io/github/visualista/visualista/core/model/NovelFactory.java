package io.github.visualista.visualista.core.model;

import java.util.*;

public enum NovelFactory {
    ;

    public static Novel createNovel() {
        Map<Integer,Scene> sceneMap = new HashMap<Integer,Scene>();
        Scene scene = new Scene();
        sceneMap.put(1, scene);
        return new Novel(sceneMap);
    }

}
