package com.lynbrookrobotics.potassium.components;

import java.util.LinkedList;

public abstract class Component<Controller> {
  private static LinkedList<Component<?>> components = new LinkedList<>();

  public static void updateComponents() {
    for (Component<?> c: components) {
      c.tick();
    }
  }

  private Controller currentController;
  private final Controller defaultController;

  /**
   * Creates a new component
   *
   * @param defaultController the controller to return to when resetToDefault() is called
   */
  public Component(Controller defaultController) {
    this.defaultController = defaultController;
    resetToDefault();

    components.add(this);
  }

  /**
   * Resets the component's controller to a default (usually no-op) one Automatically called when a
   * robot enters disabled mode
   */
  public void resetToDefault() {
    setController(defaultController);
  }

  /**
   * Replaces the current component controller with the one provided
   *
   * @param controller the controller to use
   */
  public void setController(Controller controller) {
    currentController = controller;
  }

  /**
   * Runs one update loop of the component
   */
  public void tick() {
    setOutputs(currentController);
  }

  /**
   * Uses the values provided by the controller to control actuators
   *
   * @param controller the controller to get data about component output from
   */
  protected abstract void setOutputs(Controller controller);
}
