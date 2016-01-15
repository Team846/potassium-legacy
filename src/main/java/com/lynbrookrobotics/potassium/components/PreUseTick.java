package com.lynbrookrobotics.potassium.components;

/**
 * An interface to mix in to a controller when the controller should be updated before use
 *
 * Examples: a controller that needs to run PID logic before motor speeds are requested from it
 */
public interface PreUseTick {
    /**
     * What to run before the controller is used during a component update
     */
    void preUseTick();
}
