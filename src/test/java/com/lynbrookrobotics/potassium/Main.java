package com.lynbrookrobotics.potassium;

import com.lynbrookrobotics.potassium.events.Event;
import com.lynbrookrobotics.potassium.events.SteadyEvent;
import com.lynbrookrobotics.potassium.events.SteadyEventHandler;

/**
 * Just a few event examples that don't require a robot to run
 */
public class Main {
    public static boolean on = false;

    public static void main(String[] args) {
        SteadyEvent myFooEvent = new SteadyEvent() {
            @Override
            protected void checkForTrigger() {
                System.out.println("CHECK FOR TRIGGERING");
                if (on) {
                    trigger();
                }
            }
        };

        myFooEvent.forEach(new SteadyEventHandler() {
            @Override
            public void onStart() {
                System.out.println("START");
            }

            @Override
            public void onRunning() {
                System.out.println("RUN");
            }

            @Override
            public void onEnd() {
                System.out.println("END");
            }
        });

        Event.updateEvents();

        on = true;
        Event.updateEvents(); // start

        Event.updateEvents(); // run
        Event.updateEvents(); // run

        on = false;
        Event.updateEvents(); // end
    }
}
