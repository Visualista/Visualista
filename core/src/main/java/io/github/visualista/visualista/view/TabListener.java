package io.github.visualista.visualista.view;

public interface TabListener {
    public enum EventType {
        SELECT, CLOSE, NAME_CHANGE
    }

    void tabEvent(Tab source, EventType eventType);
}
