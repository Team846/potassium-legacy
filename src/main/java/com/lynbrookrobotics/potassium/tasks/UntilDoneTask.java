package com.lynbrookrobotics.potassium.tasks;

/**
 * A finite task that runs a finite task, but also a continuous task until the finite task is
 * finished
 */
public class UntilDoneTask extends FiniteTask {
  FiniteTask baseTask;
  ContinuousTask untilDoneTask;

  /**
   * Creates a new finite task with the untilDoneTask run in parallel until baseTask finishes
   *
   * This constructor should NOT be used, the task1.andUntilDone(task2) method should be used
   * instead
   *
   * @param baseTask      the reference finite task
   * @param untilDoneTask the parallel continuous task
   */
  UntilDoneTask(FiniteTask baseTask, ContinuousTask untilDoneTask) {
    this.baseTask = baseTask;
    this.untilDoneTask = untilDoneTask;
  }

  @Override
  protected void startTask() {
    baseTask.init();
    untilDoneTask.init();
  }

  @Override
  protected void update() {
    if (baseTask.isFinished()) {
      finished();
    } else {
      baseTask.tick();
      untilDoneTask.tick();
    }
  }

  @Override
  protected void endTask() {
    baseTask.abort();
    untilDoneTask.abort();
  }
}
