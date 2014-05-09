package io.github.visualista.visualista.model;

public class IdGenerator {

    private int id;

    public IdGenerator() {
        id = 0;
    }

    public int generateId() {
        id++;
        return id;
    }

}
