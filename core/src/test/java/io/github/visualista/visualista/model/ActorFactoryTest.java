package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ActorFactoryTest {

    private ActorFactory factory;
    private Actor actor;

    @Before
    public void setUp() throws Exception {
        factory = new ActorFactory();
        actor = factory.createActor();
    }

    @Test
    public void test() {
        assertThat(factory, notNullValue());
    }

    @Test
    public void testCreateActor() {
        assertThat(actor, notNullValue());
        assertThat(actor.getImage(), notNullValue());
        assertThat(actor.getActions(), notNullValue());
    }

}
