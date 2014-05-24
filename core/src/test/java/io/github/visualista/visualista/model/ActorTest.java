package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testImage() {
        Actor actor2 = new Actor();
        actor2.setImage(new Image(null));
        assertNotNull(actor2.getImage());
    }

    @Test
    public void testActions() {
        Actor actor2 = new Actor();
        IAction action = new IAction() {

            @Override
            public Object getActionData() {

                return null;

            }

        };
        actor2.addAction(action);
        assertEquals(1, actor2.getActions().size());
        actor2.removeAction(action);
        assertEquals(0, actor2.getActions().size());
    }

    @Test
    public void testGetAndSetNameAndToString() {
        Actor actor = new Actor();
        String testString = "Something, something, dark side";
        actor.setName(null);
        assertThat("",equalTo(actor.getName()));
        actor.setName(testString);
        assertEquals(testString, actor.getName());
        assertEquals(testString, actor.toString());
    }

}
