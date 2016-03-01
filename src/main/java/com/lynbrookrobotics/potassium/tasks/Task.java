package com.lynbrookrobotics.potassium.tasks;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class Task {
  private static Optional<Task> currentTask = Optional.empty();

  public static void executeTask(Task task) {
    if (DriverStation.getInstance().isEnabled()) {
      currentTask.ifPresent(Task::abort);
      currentTask = Optional.of(task);
      task.init();
    }
  }

  public static void abortTask(Task task) {
    currentTask.ifPresent(cur -> {
      if (cur == task) {
        cur.abort();
        currentTask = Optional.empty();
      }
    });
  }

  public static void abortCurrentTask() {
    currentTask.ifPresent(Task::abortTask);
  }

  public static void updateCurrentTask() {
    if (DriverStation.getInstance().isDisabled()) {
      abortCurrentTask();
    } else {
      currentTask.ifPresent(Task::tick);
    }
  }

  /**
   * Initializes the task
   */
  protected abstract void init();

  /**
   * Runs one iteration of the task
   */
  protected abstract void tick();

  /**
   * Aborts a (possible running) task
   */
  protected abstract void abort();
}
