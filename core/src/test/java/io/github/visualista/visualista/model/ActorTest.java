package io.github.visualista.visualista.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import io.github.visualista.visualista.core.model.Action;
import io.github.visualista.visualista.core.model.Actor;
import io.github.visualista.visualista.core.model.Image;

import org.hamcrest.text.StringContainsInOrder;
import org.junit.Before;
import org.junit.Test;

public class ActorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testId() {
		Actor actor = new Actor();
		assertNotNull(actor);
		assertEquals(0, actor.getId());
		Actor actor2 = new Actor(4);
		assertEquals(actor2.getId(), 4);
	}

	@Test
	public void testImage() {
		Actor actor2 = new Actor(4);
		actor2.setImage(new Image());
		assertNotNull(actor2.getImage());
	}

	@Test
	public void testActions() {
		Actor actor2 = new Actor(4);
		Action action = new Action() {
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
	@Test
    public void testToString(){
		Actor actor = new Actor();
		assertThat(actor.toString(),StringContainsInOrder.stringContainsInOrder(Arrays.asList(""+actor.getId(),""+actor.getName())));
	}
}
