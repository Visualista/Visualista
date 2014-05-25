package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.github.visualista.visualista.util.Point;

import org.junit.Before;
import org.junit.Test;

public class SetActorActionTest {

    private static final int POINT_3_Y = 4;
    private static final int POINT_3_X = 3;
    private static final int POINT_2_Y = 2;
    private static final int POINT_2_X = 3;
    private static final int POINT_1_Y = 3;
    private static final int POINT_1_X = 2;
    private Point point;
    private SetActorAction setActorAction;
    private Actor actor;

    @Before
    public void setUp() {

        point = new Point(POINT_1_X, POINT_1_Y);
        actor = new Actor();
        setActorAction = new SetActorAction(point, actor);
    }

    @Test
    public void testSetAndGetTagetTile() {
        Point point2 = new Point(POINT_2_X, POINT_2_Y);
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
    public void testGetWrappedData() {
        PositionedActor positionedActor = new PositionedActor(new Point(
                POINT_3_X, POINT_3_Y), actor);
        SetActorAction setActorAction2 = new SetActorAction(positionedActor);
        assertThat(setActorAction2.getWrappedData().getPosition(),
                equalTo(positionedActor.getPosition()));
        assertThat(setActorAction2.getWrappedData().getActor(),
                equalTo(positionedActor.getActor()));
    }

    @Test
    public void testGetActionData() {
        assertThat(
                ((PositionedActor) (setActorAction.getActionData()))
                        .getPosition(),
                equalTo(point));
        assertThat(
                ((PositionedActor) (setActorAction.getActionData())).getActor(),
                equalTo(actor));
    }
}
