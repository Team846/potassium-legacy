package com.lynbrookrobotics.potassium;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.potassium.events.Event;
import com.lynbrookrobotics.potassium.tasks.Task;

public class Potassium {
  /**
   * Updates (in order): events, tasks, and components
   */
  public static void updateAll() {
    Event.updateEvents();
    Task.updateCurrentTask();
    Component.updateComponents();
  }
}
