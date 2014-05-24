package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.github.visualista.visualista.util.Point;

import org.junit.Before;
import org.junit.Test;

public class SetActorActionTest {

    private Point point;
    private SetActorAction setActorAction;
    private Actor actor;

    @Before
    public void setUp() {

        point = new Point(2, 3);
        actor = new Actor();
        setActorAction = new SetActorAction(point, actor);
    }

    @Test
    public void testSetAndGetTagetTile() {
        Point point2 = new Point(3, 2);
        setActorAction.setTargetTile(point2);
        assertThat(point2, equalTo(setActorAction.getTargetTile()));
    }

    @Test
    public void testSetAndGetReplaceMentActor() {
        Actor actor2 = new Actor();
        setActorAction.setReplacementActor(actor2);
        assertThat(actor2, equalTo(setActorAction.getReplacementActor()));
    }

    @Test
    public void testToString() {
        assertThat("Place " + actor.getName() + " on tile  " + point.getX()
                + "," + point.getY(), equalTo(setActorAction.toString()));
    }

    @Test
    public void testGetExplainatoryName() {
        assertThat("SetTile(" + actor.toString() + " | " + point.getX() + ","
                + point.getY() + ")",
                equalTo(setActorAction.getExplainatoryName()));
    }
    
    @Test
    public void testGetWrappedDataAndGetActionData(){
        PositionedActor positionedActor = new PositionedActor(new Point(3,4), actor);
        SetActorAction setActorAction2 = new SetActorAction(positionedActor);
        assertThat(setActorAction2.getWrappedData(),equalTo(positionedActor));
        assertThat(positionedActor, equalTo((PositionedActor)(setActorAction2.getActionData())));
    }
}
