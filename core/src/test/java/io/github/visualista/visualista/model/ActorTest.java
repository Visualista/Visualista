package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Action;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Image;

import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Actor actor = new Actor();
        assertNotNull(actor);
        assertEquals(0, actor.getId());
        Actor actor2 = new Actor(4);
        Action action = new Action() {
        };
        actor2.addAction(action);
        assertTrue(actor2.getActions().size() == 1);
        actor2.removeAction(action);
        assertTrue(actor2.getActions().size() == 0);
        actor2.setImage(new Image());
        assertNotNull(actor2.getImage());
        assertEquals(actor2.getId(),4);
    }

}
