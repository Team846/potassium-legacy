package com.lynbrookrobotics.potassium.defaults.events;

import com.lynbrookrobotics.potassium.events.ImpulseEvent;
import edu.wpi.first.wpilibj.Joystick;

public class ButtonPress extends ImpulseEvent {
    private static final boolean BUTTON_HELD = true;
    private static final boolean BUTTON_NOT_HELD = false;

    Joystick joystick;
    int button;
    boolean lastState;

    public ButtonPress(Joystick joystick, int button)
    {
        this.joystick = joystick;
        this.button = button;
        this.lastState = joystick.getRawButton(button);
    }

    @Override
    protected void checkForTrigger() {
        boolean currentState = joystick.getRawButton(this.button);

        if (this.lastState == BUTTON_NOT_HELD && currentState == BUTTON_HELD) {
            trigger();
        }

        lastState = currentState;
    }
}
