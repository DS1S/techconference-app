package backend.systems.events.subsystems;

import backend.systems.SubSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * An abstract subsystem of EventSystem that allows the user to perform a particular action related to the sign up
 * of Events.
 */
public abstract class EventSubSystem extends SubSystem {
    protected final EventManager eventManager;
    protected final UserManager userManager;
    protected final EventUI eventUI;
    protected final int numOptions;

    /**
     * Constructs a new EventSubSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventSubSystem.
     * @param userManager The UserManager that will be used by the EventSubSystem.
     * @param eventUI The EventUI that will be used by the EventSubSystem.
     * @param numOptions The number of menu options given by the EventSubSystem.
     */
    public EventSubSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(numOptions, new OptionIndexProcessor(new Scanner(System.in), numOptions));
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = eventUI;
        this.numOptions = numOptions;
    }

    /**
     * Displays the given events using eventsData and takes in an integer input from the user.
     * @param eventsData The data of the events to be displayed.
     * @return A valid input from the user if there are events in eventsData. 0 otherwise.
     */
    protected int processEvents(List<Map<String, Object>> eventsData) {
        eventUI.displayEvents(eventsData);
        if (!eventsData.isEmpty()) {
            IndexProcessor<Integer> eventProcessor = new OptionIndexProcessor(input, eventsData.size());
            eventUI.displayEnterIndexEvent();
            return eventProcessor.processInput();
        }
        return 0;
    }
}