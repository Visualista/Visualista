package io.github.visualista.visualista.model;

import io.github.visualista.visualista.util.Point;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class PositionedActorTest {
    
    private static final int MAX_HEIGHT = 150;
    private static final int MAX_WIDTH = 150;
    private int x;
    private int y;
    private Point position;
    private Actor actor;
    private PositionedActor positionedActor;

    @Before
    public void setUp(){
        Random rng = new Random();
        x = rng.nextInt(MAX_WIDTH);
        y = rng.nextInt(MAX_HEIGHT);
        position = new Point(x,y);
        actor = new Actor();
        positionedActor = new PositionedActor(position,actor);
    }

    @Test
    public void testGetPosition(){
        assertThat(position, equalTo(positionedActor.getPosition()));
    }

    @Test
    public void testGetActor(){
        assertThat(actor, equalTo(positionedActor.getActor()));
    }
}
