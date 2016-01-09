package com.lynbrookrobotics.potassium.tasks;

/**
 * Represents a continuous task where two continuous tasks are run in parallel
 */
public class ContinuousParallelTask extends ContinuousTask {
    ContinuousTask taskA;
    ContinuousTask taskB;

    /**
     * Creates a new task where two continuous tasks are run in parallel
     *
     * This constructor should NOT be used, the task1.and(task2) method should be used instead
     * @param taskA one of the tasks to run
     * @param taskB the other task to run
     */
    ContinuousParallelTask(ContinuousTask taskA, ContinuousTask taskB) {
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
        taskA.tick();
        taskB.tick();
    }

    @Override
    protected void endTask() {
        taskA.abort();
        taskB.abort();
    }
}
