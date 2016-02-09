package com.lynbrookrobotics.potassium.components;

import javaslang.Tuple;
import javaslang.Tuple2;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Component<Controller> {
  private static LinkedList<Component<?>> components = new LinkedList<>();

  public static void updateComponents() {
    components.forEach(Component::tick);
  }

  private Controller currentController;
  private final Controller defaultController;
  private LinkedList<Tuple2<Supplier<Boolean>, Function<Controller, Controller>>> safeties = new LinkedList<>();

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
   * Attaches a new safety to this component, which can override controllers when it is triggered
   *
   * @param trigger         a boolean supplier that represents if the safety should be triggered
   * @param safetyTransform a way to transform one controller to another by applying necessary
   *                        safety efforts
   */
  public void attachSafety(Supplier<Boolean> trigger, Function<Controller, Controller> safetyTransform) {
    safeties.add(Tuple.of(trigger, safetyTransform));
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
    Controller toUse = safeties.stream().
        filter(t -> t._1.get()).
        map(Tuple2::_2).
        reduce(
            currentController,
            (acc, cur) -> cur.apply(acc),
            (control1, control2) -> {
              throw new RuntimeException("Combining controllers makes no sense");
            }
        );

    if (toUse instanceof PreUseTick) {
      ((PreUseTick) toUse).preUseTick();
    }

    setOutputs(toUse);
  }

  /**
   * Uses the values provided by the controller to control actuators
   *
   * @param controller the controller to get data about component output from
   */
  protected abstract void setOutputs(Controller controller);
}
