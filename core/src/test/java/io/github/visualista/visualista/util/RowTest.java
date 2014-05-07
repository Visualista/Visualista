package io.github.visualista.visualista.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

public class RowTest {
    private static final int ROW_OF_LENGTH_5_LENGTH = 5;
    private static final int A_NUMBER = 10;
    private Row<Integer> rowOfLength5;

    @Before
    public void setUp() {
        rowOfLength5 = new Row<Integer>(ROW_OF_LENGTH_5_LENGTH);
    }

    @Test
    public void testCopyConstructor() {
        Row<Integer> clonedRow = new Row<Integer>(rowOfLength5);
        assertThat(clonedRow, equalTo(rowOfLength5));

    }

    @Test
    public void testGetLength() {
        assertThat(rowOfLength5.getLength(), equalTo(ROW_OF_LENGTH_5_LENGTH));
    }

    @Test
    public void testSetAndGetAt() {
        for (int i = 0; i < rowOfLength5.getLength(); ++i) {
            assertThat(rowOfLength5.getAt(i), nullValue());
        }
        for (int i = 0; i < rowOfLength5.getLength(); ++i) {
            rowOfLength5.setAt(i, A_NUMBER - i);
        }
        for (int i = 0; i < rowOfLength5.getLength(); ++i) {
            assertThat(rowOfLength5.getAt(i), is(A_NUMBER - i));
        }
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Row.class).verify();
    }

}
