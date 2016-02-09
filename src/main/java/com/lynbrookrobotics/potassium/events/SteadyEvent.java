package com.lynbrookrobotics.potassium.events;

import com.lynbrookrobotics.potassium.tasks.Task;

import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * Represents an event with defined start, running, and end phases
 *
 * Example: button is held down
 */
public abstract class SteadyEvent extends Event {

  private LinkedList<SteadyEventHandler> handlers = new LinkedList<>();
  private boolean isRunning = false;
  private boolean justTriggered = false;

  /**
   * Runs a single loop of this event, where it is checked if the event should be triggered
   */
  @Override
  protected void tick() {
    justTriggered = false;
    checkForTrigger();

    if (justTriggered) {
      if (isRunning) {
        handlers.forEach(SteadyEventHandler::onRunning);
      } else { // we were not running but the event was triggered
        isRunning = true;
        handlers.forEach(SteadyEventHandler::onStart);
      }
    } else if (isRunning) { // we were running but the event was not triggered
      isRunning = false;
      handlers.forEach(SteadyEventHandler::onEnd);
    }
  }

  public void forEach(SteadyEventHandler handler) {
    handlers.add(handler);
  }

  public void forEach(Runnable handler) {
    forEach(new SteadyEventHandler() {
      @Override
      public void onStart() {
      }

      @Override
      public void onRunning() {
        handler.run();
      }

      @Override
      public void onEnd() {
      }
    });
  }

  public void forEach(Runnable start, Runnable end) {
    forEach(new SteadyEventHandler() {
      @Override
      public void onStart() {
        start.run();
      }

      @Override
      public void onRunning() {
      }

      @Override
      public void onEnd() {
        end.run();
      }
    });
  }

  public void forEach(Supplier<Task> taskToExecute) {
    forEach(new SteadyEventHandler() {
      Task currentTask;

      @Override
      public void onStart() {
        currentTask = taskToExecute.get();
        Task.executeTask(currentTask);
      }

      @Override
      public void onRunning() {}

      @Override
      public void onEnd() {
        Task.abortTask(currentTask);
        currentTask = null;
      }
    });
  }

  public void forEach(Task taskToExecute) {
    forEach(() -> taskToExecute);
  }

  /**
   * A call to this method from checkForTrigger will mark the event as triggered for a single loop
   */
  protected void trigger() {
    justTriggered = true;
  }

  /**
   * What to do to check if the event should be triggered
   */
  protected abstract void checkForTrigger();
}
