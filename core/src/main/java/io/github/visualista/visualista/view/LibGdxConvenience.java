package io.github.visualista.visualista.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public enum LibGdxConvenience {
    ;
    public static void addActorsTo(final Group group, final Actor... actors) {
        for (Actor actor : actors) {
            group.addActor(actor);
        }
    }
}
