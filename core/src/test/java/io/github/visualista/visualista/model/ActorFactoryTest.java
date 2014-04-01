package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.ActorFactory;

import org.junit.Test;

public class ActorFactoryTest {

    @Test
    public void test() {
        ActorFactory actor = new ActorFactory();
        Actor actor1 = actor.createActor();
        Actor actor2 = actor.createActor();
        assertNotEquals(actor1.getId(), actor2.getId());
        assertEquals(actor1.getId(),1);
        assertEquals(actor2.getId(),2);
    }

}