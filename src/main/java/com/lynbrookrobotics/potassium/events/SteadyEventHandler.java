package com.lynbrookrobotics.potassium.events;

/**
 * Represents a handler for a steady event
 */
public abstract class SteadyEventHandler {
  /**
   * What to do when the event starts
   */
  public abstract void onStart();

  /**
   * What to do as the event is running
   */
  public abstract void onRunning();

  /**
   * What to do when the event end
   */
  public abstract void onEnd();
}
