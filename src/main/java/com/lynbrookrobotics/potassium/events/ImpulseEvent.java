package com.lynbrookrobotics.potassium.events;

import java.util.LinkedList;

/**
 * Represents an event that happens in one go
 *
 * Example: button is released
 */
public abstract class ImpulseEvent extends Event {
  private LinkedList<Runnable> handlers = new LinkedList<>();

  /**
   * Runs a single loop of this event, where it is checked if the event should be triggered
   */
  @Override
  public void tick() {
    checkForTrigger();
  }

  /**
   * Attaches a handler to this event to be called when the event is triggered
   *
   * @param handler the handler to attach
   */
  public void forEach(Runnable handler) {
    handlers.add(handler);
  }

  /**
   * A call to this method from checkForTrigger will mark the event as triggered for a single loop
   */
  protected void trigger() {
    handlers.forEach(Runnable::run);
  }

  /**
   * What to do to check if the event should be triggered
   */
  protected abstract void checkForTrigger();
}
