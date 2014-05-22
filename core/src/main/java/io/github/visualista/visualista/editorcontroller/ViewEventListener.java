package io.github.visualista.visualista.editorcontroller;

/** Interface responsible of communicating relevant methods to the
 * viewEventSource
 * @author Markus Bergland, Erik Risfeltd, Pierre Krafft
 *
 */
public interface ViewEventListener {

    void handleViewEvent(EditorViewEvent event);

}
