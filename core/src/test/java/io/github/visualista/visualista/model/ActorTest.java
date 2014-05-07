package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.*;

import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testImage() {
        Actor actor2 = new Actor();
        actor2.setImage(new Image());
        assertNotNull(actor2.getImage());
    }

    @Test
    public void testActions() {
        Actor actor2 = new Actor();
        IAction action = new IAction() {

            @Override
            public String getExplainatoryName() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void callAction() {
                // TODO Auto-generated method stub

            }

        };
        actor2.addAction(action);
        assertEquals(1, actor2.getActions().size());
        actor2.removeAction(action);
        assertEquals(0, actor2.getActions().size());
    }

    @Test
    public void testGetAndSetName() {
        Actor actor = new Actor();
        String testString = "Something, something, dark side";
        actor.setName(testString);
        assertEquals(testString, actor.getName());
    }

}
