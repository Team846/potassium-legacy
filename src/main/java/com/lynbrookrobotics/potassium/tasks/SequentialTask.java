package com.lynbrookrobotics.potassium.tasks;

/**
 * Represents a finite task where one finite task is run after the other
 *
 * Can be created by using the {@code then} method on finite tasks
 */
public class SequentialTask extends FiniteTask {
    private enum SequentialState {
        RUNNING_FIRST,
        RUNNING_SECOND
    }

    private SequentialState currentState = SequentialState.RUNNING_FIRST;
    private FiniteTask firstTask;
    private FiniteTask secondTask;

    /**
     * Creates a new finite task where one task is followed by another
     *
     * This constructor should NOT be used, the task1.then(task2) method should be used instead
     * @param firstTask the first task to run
     * @param secondTask the task to run after the first task completes
     */
    SequentialTask(FiniteTask firstTask, FiniteTask secondTask) {
        this.firstTask = firstTask;
        this.secondTask = secondTask;
    }

    @Override
    protected void startTask() {
        currentState = SequentialState.RUNNING_FIRST;
        firstTask.init();
    }

    @Override
    protected void update() {
        if (currentState == SequentialState.RUNNING_FIRST) {
            if (firstTask.isFinished()) {
                currentState = SequentialState.RUNNING_SECOND;
                secondTask.init();
            } else {
                firstTask.tick();
            }
        } else {
            if (secondTask.isFinished()) {
                finished();
            } else {
                secondTask.tick();
            }
        }
    }

    @Override
    protected void endTask() {
        currentState = SequentialState.RUNNING_FIRST;
        firstTask.abort();
        secondTask.abort();
    }
}
