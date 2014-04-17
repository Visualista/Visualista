package io.github.visualista.visualista.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

public class RowTest {
	private Row<Integer> rowOfLength5;

	@Before
	public void setUp(){
		rowOfLength5 = new Row<Integer>(5);
	}

	@Test
	public void testCopyConstructor() {
		Row<Integer> clonedRow = new Row<Integer>(rowOfLength5);
		assertThat(clonedRow,equalTo(rowOfLength5));
		
	}

	@Test
	public void testGetLength() {
		assertThat(rowOfLength5.getLength(),equalTo(5));
	}

	@Test
	public void testSetAndGetAt() {
		for(int i=0;i<5;++i){
			assertThat(rowOfLength5.getAt(i),nullValue());
		}
		for(int i=0;i<5;++i){
			rowOfLength5.setAt(i, 10-i);
		}
		for(int i=0;i<5;++i){
			assertThat(rowOfLength5.getAt(i),is(10-i));
		}
	}
	
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Dimension.class).verify();
	}

}
