package io.github.visualista.visualista.model;

import static org.junit.Assert.*;
import io.github.visualista.visualista.core.model.IdGenerator;

import org.junit.Test;

public class IdGeneratorTest {

    @Test
    public void test() {
        IdGenerator id = new IdGenerator();
        assertNotEquals(id.generateId(), id.generateId());
    }

}
