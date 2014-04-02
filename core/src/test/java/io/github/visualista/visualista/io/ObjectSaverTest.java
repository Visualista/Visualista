package io.github.visualista.visualista.io;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import io.github.visualista.visualista.core.model.Actor;

import org.junit.Test;

public class ObjectSaverTest {

	@Test
	public void testSaveCollection() {
		XStreamManager manager = new XStreamManager();
		ObjectSaver<Actor> saver = new ObjectSaver<Actor>(manager.getMainXStream(), new File("C:\\VISUALISTA\\"));
		ArrayList<Actor> arr= new ArrayList<Actor>();
		arr.add(new Actor(1));
		arr.add(new Actor(4));
		saver.saveCollection(arr);
	}

}
