package com.lynbrookrobotics.potassium.tasks;

/**
 * A finite task that runs a finite task, but also a continuous task until the finite task is
 * finished
 */
public class TimeoutTask extends FiniteTask {
  FiniteTask baseTask;
  long timeout;

  /**
   * Creates a new finite task with the untilDoneTask run in parallel until baseTask finishes
   *
   * This constructor should NOT be used, the task1.andUntilDone(task2) method should be used
   * instead
   *
   * @param baseTask      the reference finite task
   */
  TimeoutTask(FiniteTask baseTask, long timeout) {
    this.baseTask = baseTask;
    this.timeout = timeout;
  }

  long startTime;

  @Override
  protected void startTask() {
    baseTask.init();
    startTime = System.currentTimeMillis();
  }

  @Override
  protected void update() {
    if (baseTask.isFinished()) {
      finished();
    } else if (System.currentTimeMillis() >= startTime + timeout) {
      finished();
    } else {
      baseTask.tick();
    }
  }

  @Override
  protected void endTask() {
    baseTask.abort();
  }
}
