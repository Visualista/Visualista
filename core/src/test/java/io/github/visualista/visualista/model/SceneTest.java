package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.GridFactory;
import io.github.visualista.visualista.core.model.Scene;




import java.util.Arrays;
import java.util.Random;

import org.hamcrest.text.StringContainsInOrder;
import org.junit.Before;
import org.junit.Test;

public class SceneTest {
    
    private GridFactory gridFactory;

    @Before
    public void setUp() throws Exception {
        gridFactory = new GridFactory();
    }

    @Test
    public void test() {
        Random rand = new Random();
        int id = rand.nextInt();
        Scene scene = new Scene(id,gridFactory.createGrid());
        assertEquals(id,scene.getId());
        assertNotNull(scene.getGrid());
    }
    
    @Test
	public void testGetAndSetName() {
		Scene scene = new Scene(0, gridFactory.createGrid());
		String testString = "Something, something, dark side";
		scene.setName(testString);
		assertEquals(testString, scene.getName());
	}
    
    @Test
    public void testToString(){
    	Scene scene = new Scene(0, gridFactory.createGrid());
    	assertThat(scene.toString(),StringContainsInOrder.stringContainsInOrder(Arrays.asList(""+scene.getId(),""+scene.getName())));
    }

}
