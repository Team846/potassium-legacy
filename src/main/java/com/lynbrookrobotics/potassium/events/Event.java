package com.lynbrookrobotics.potassium.events;

import java.util.LinkedList;

public abstract class Event {
    protected static LinkedList<Event> events = new LinkedList<>();

    public static void updateEvents() {
        events.forEach(Event::tick);
    }

    protected Event() {
        events.add(this);
    }

    protected abstract void tick();
}
