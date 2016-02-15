package com.lynbrookrobotics.potassium.defaults.events;

import com.lynbrookrobotics.potassium.events.SteadyEvent;
import edu.wpi.first.wpilibj.Joystick;

public class ButtonHold extends SteadyEvent {
    Joystick joystick;
    int button;

    public ButtonHold(Joystick joystick, int button) {
        this.joystick = joystick;
        this.button = button;
    }

    @Override
    protected void checkForTrigger() {
        if (joystick.getRawButton(this.button)) {
            trigger();
        }
    }
}
