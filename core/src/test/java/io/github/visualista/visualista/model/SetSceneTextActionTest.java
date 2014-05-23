package io.github.visualista.visualista.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SetSceneTextActionTest {

    private SetSceneTextAction action;
    private String firstString;
    private String secondString;
    private String shortString;

    @Before
    public void setUp(){
        firstString = "something";
        secondString = "something else";
        shortString = "my";
        action = new SetSceneTextAction(firstString);
    }

    @Test
    public void testSetAndGetSceneText(){
        action.setSceneText(secondString);
        assertThat(secondString,equalTo(action.getSceneText()));
    }

    @Test
    public void testToStringAndGetExplainatoryName(){
        assertThat("Change Scene text to\"" + secondString.substring(0, 5) + "...\"", equalTo(action.toString()));
        action.setSceneText(shortString);
        assertThat("Change Scene text to \"" + shortString + "\"", equalTo(action.getExplainatoryName()));
    }

    @Test
    public void testGetActionData(){
        assertThat(shortString,equalTo((String)action.getActionData()));
    }
}
