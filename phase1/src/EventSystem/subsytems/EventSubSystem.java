package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Main.SubSystem;
import Presenters.EventUI;
import coreUtil.InputProcessors.IndexProcessor;
import coreUtil.InputProcessors.OptionIndexProcessor;


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
     * Constructs a new EventMenuSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventMenuSystem.
     * @param userManager The UserManager that will be used by the EventMenuSystem.
     * @param eventUI The EventUI that will be used by the EventMenuSystem.
     * @param numOptions The number of menu options given by the EventMenuSystem.
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
