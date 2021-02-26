package de.zekro.log4forge.logging;

/**
 * Event Log Type Enum.
 */
public enum EventType {

    BLOCK_PLACE("block_place"),
    BLOCK_BREAK("block_break"),
    FARMLAND_TRAMPLE("farmland_trample"),
    OPEN_CONTAINER("open_container_event"),
    CLOSE_CONTAINER("close_container")
    ;

    private final String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
