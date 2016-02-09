package com.lynbrookrobotics.potassium.tasks;

/**
 * Represents a finite task where two finite tasks are run in parallel
 */
public class FiniteParallelTask extends FiniteTask {
  FiniteTask taskA;
  FiniteTask taskB;

  /**
   * Creates a new finite task where the two passed tasks are run in parallel
   *
   * This constructor should NOT be used, the task1.and(task2) method should be used instead
   *
   * @param taskA one of the tasks to run
   * @param taskB the other task to run
   */
  FiniteParallelTask(FiniteTask taskA, FiniteTask taskB) {
    this.taskA = taskA;
    this.taskB = taskB;
  }

  @Override
  protected void startTask() {
    taskA.init();
    taskB.init();
  }

  @Override
  protected void update() {
    if (taskA.isFinished() && taskB.isFinished()) {
      finished();
    } else {
      taskA.tick();
      taskB.tick();
    }
  }

  @Override
  protected void endTask() {
    if (!taskA.isFinished()) {
      taskA.abort();
    }

    if (!taskB.isFinished()) {
      taskB.abort();
    }
  }
}
