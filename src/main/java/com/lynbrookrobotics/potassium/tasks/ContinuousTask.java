package com.lynbrookrobotics.potassium.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Task that has a defined start and running, but no finished phase
 *
 * Example use:
 *  A task that spins the rollers on your collector.
 *  This would not have an end, but could be mapped to a button to run while the button is held down.
 */
public abstract class ContinuousTask extends Task {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void init() {
        startTask();
    }

    @Override
    protected void tick() {
        update();
    }

    @Override
    protected void abort() {
        logger.debug("I am ending");
        endTask();
    }

    /**
     * Creates a task where this task and the passed task are run in parallel
     * @param otherTask the task to run in parallel with this task
     * @return the combined task with this and the other task in parallel
     */
    public ContinuousParallelTask and(ContinuousTask otherTask) {
        return new ContinuousParallelTask(this, otherTask);
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
     * What to do at the end of the task, called from abort()
     * Should include things such as resetting component controllers and internal state
     * The task should be in the same state as it was before it was run after calling this method
     */
    protected abstract void endTask();
}
