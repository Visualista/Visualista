package io.github.visualista.visualista.core.model;

import java.util.ArrayList;
import java.util.List;

public enum NovelFactory {
    ;

    public static Novel createNovel() {
        List<Scene> sceneList = new ArrayList<Scene>();
        Scene scene = new Scene();
        sceneList.add(scene);
        return new Novel(sceneList);
    }

}
