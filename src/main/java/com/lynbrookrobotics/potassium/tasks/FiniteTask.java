package com.lynbrookrobotics.potassium.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Task that has a defined start, running, and finished phase
 */
public abstract class FiniteTask extends Task {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  private boolean isFinished = false;

  @Override
  protected void init() {
    isFinished = false;
    startTask();
  }

  @Override
  protected void tick() {
    if (!isFinished) {
      update();
    }
  }

  @Override
  protected void abort() {
    if (!isFinished) {
      logger.debug("I am aborting");
      isFinished = true;
      endTask();
    }
  }

  /**
   * Checks if the task has been completed
   *
   * @return a boolean, true if the task is completed, false otherwise
   */
  public boolean isFinished() {
    return isFinished;
  }

  /**
   * Creates a task where this task is followed by the passed task
   *
   * @param nextTask the task to run after this task
   * @return the combined task with this followed by nextTask
   */
  public SequentialTask then(FiniteTask nextTask) {
    return new SequentialTask(this, nextTask);
  }

  /**
   * Creates a task where this task and the passed task are run in parallel
   *
   * @param otherTask the task to run in parallel with this task
   * @return the combined task with this and the other task in parallel
   */
  public FiniteParallelTask and(FiniteTask otherTask) {
    return new FiniteParallelTask(this, otherTask);
  }

  /**
   * Creates a task where a continuous task is run in parallel with this task until this task
   * completes
   *
   * @param untilDoneTask the continuous task to run in parallel
   * @return the combined task with this task and the passed task in parallel
   */
  public UntilDoneTask andUntilDone(ContinuousTask untilDoneTask) {
    return new UntilDoneTask(this, untilDoneTask);
  }

  /**
   * Converts this task to a continuous one.
   */
  public ContinuousTask toContinuous() {
    FiniteTask outer = this;

    return new ContinuousTask() {
      @Override
      protected void startTask() {
        outer.init();
      }

      @Override
      protected void update() {
        outer.update();
      }

      @Override
      protected void endTask() {
        outer.abort();
      }
    };
  }

  /**
   * Mark the task as finished; should be called from within update to mark the task as completed
   */
  protected void finished() {
    logger.debug("I have been marked finished");
    isFinished = true;
    endTask();
  }

  /***
   * What to do at the beginning of the task
   */
  protected abstract void startTask();

  /**
   * What to do on each iteration of this task
   */
  protected abstract void update();

  /**
   * What to do at the end of the task (either gracefully from finished() or from abort()) Should
   * include things such as resetting component controllers and internal state The task should be in
   * the same state as it was before it was run after calling this method
   */
  protected abstract void endTask();
}
