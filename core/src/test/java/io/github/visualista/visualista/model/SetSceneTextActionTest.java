package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SetSceneTextActionTest {

    private static final int SCENE_TEXT_MAX_LENGTH_BEFORE_OVERFLOW = 5;
    private SetSceneTextAction action;
    private String firstString;
    private String secondString;
    private String shortString;

    @Before
    public void setUp() {
        firstString = "something";
        secondString = "something else";
        shortString = "my";
        action = new SetSceneTextAction(firstString);
    }

    @Test
    public void testSetAndGetSceneText() {
        action.setSceneText(secondString);
        assertThat(secondString, equalTo(action.getSceneText()));
    }

    @Test
    public void testToStringAndGetExplainatoryName() {
        assertThat("Change Scene text to\"" + secondString.substring(0, SCENE_TEXT_MAX_LENGTH_BEFORE_OVERFLOW)
                + "...\"", equalTo(action.toString()));
        action.setSceneText(shortString);
        assertThat("Change Scene text to \"" + shortString + "\"",
                equalTo(action.getExplainatoryName()));
    }

    @Test
    public void testGetActionData() {
        assertThat(firstString, equalTo((String) action.getActionData()));
    }
}
