package com.lynbrookrobotics.potassium.events;

import java.util.LinkedList;

/**
 * Represents something that can be triggered and can have attached handlers that run on triggers
 */
public abstract class Event {
    protected static LinkedList<Event> events = new LinkedList<>();

    /**
     * Runs one full iteration of all the events that have been constructed
     */
    public static void updateEvents() {
        events.forEach(Event::tick);
    }

    /**
     * Constructor for events, which adds the event to the list of all events
     */
    protected Event() {
        events.add(this);
    }

    /**
     * Runs one iteration of the event, where triggers should be checked
     */
    protected abstract void tick();
}
