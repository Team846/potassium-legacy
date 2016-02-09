package com.lynbrookrobotics.potassium.defaults.events;

import com.lynbrookrobotics.potassium.events.SteadyEvent;

import edu.wpi.first.wpilibj.DriverStation;

public class InGameState extends SteadyEvent {
  public enum GameState {
    DISABLED,
    AUTONOMOUS,
    ENABLED
  }

  DriverStation station;
  GameState expectedState;

  public InGameState(DriverStation station, GameState expectedState) {
    this.station = station;
    this.expectedState = expectedState;
  }

  @Override
  protected void checkForTrigger() {
    if (expectedState == GameState.DISABLED && station.isDisabled()) {
      trigger();
    } else if (expectedState == GameState.AUTONOMOUS && station.isAutonomous() && station.isEnabled()) {
      trigger();
    } else if (expectedState == GameState.ENABLED && station.isOperatorControl() && station.isEnabled()) {
      trigger();
    }
  }
}
