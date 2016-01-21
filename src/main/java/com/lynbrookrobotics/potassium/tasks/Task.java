package com.lynbrookrobotics.potassium.tasks;

import java.util.Optional;

public abstract class Task {
    private static Optional<Task> currentTask = Optional.empty();

    public static void executeTask(Task task) {
        currentTask.ifPresent(Task::abort);
        currentTask = Optional.of(task);
        task.init();
    }

    public static void abortTask(Task task) {
        currentTask.ifPresent(cur -> {
            if (cur == task) {
                cur.abort();
                currentTask = Optional.empty();
            }
        });
    }

    public static void updateCurrentTask() {
        currentTask.ifPresent(Task::tick);
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
