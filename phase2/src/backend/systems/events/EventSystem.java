package backend.systems.events;

import backend.entities.users.Perms;
import backend.systems.MenuSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import utility.RunnableSystem;

import frontend.EventUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An EventSystem that allows the user to perform actions related to the sign up and scheduling of events.
 */
public class EventSystem extends MenuSystem {
    private final EventManager eventManager;
    private final UserManager userManager;
    private final EventUI eventUI;
    private final Map<Integer, RunnableSystem> subSystems;
    private List<String> optionNames;

    /**
     * Constructs a new EventSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventSystem.
     * @param userManager The UserManager that will be used by the EventSystem.
     */
    public EventSystem(EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.subSystems = new HashMap<>();
        this.optionNames = new ArrayList<>();
        this.eventUI = new EventUI(this.userManager);
        initializeSubSystems();
        changeNumOptions(subSystems.size() + 1);
    }

    /**
     * Displays available menu options for actions pertaining to the sign up and scheduling of events.
     */
    @Override
    protected void displayOptions() {
        eventUI.displayOptions(optionNames, true, true);
    }

    /**
     * Runs the subsystem of this system corresponding to the given index, if such a subsystem exists.
     * @param index The index of the subsystem.
     */
    @Override
    protected void processInput(int index) {
        if (subSystems.containsKey(index)) {
            subSystems.get(index).run();
        }
    }

    private void initializeSubSystems() {
        EventSubSystemFactory subSystemFactory = new EventSubSystemFactory();
        if (userManager.loggedInHasPermission(Perms.CAN_SCHEDULE)) {
            subSystems.put(1, subSystemFactory.createEventSubSystem("scheduler", eventManager, userManager,
                    eventUI));
        }
        else if (userManager.loggedInHasPermission(Perms.CAN_SIGN_UP_EVENT)) {
            subSystems.put(1, subSystemFactory.createEventSubSystem("signup", eventManager, userManager,
                    eventUI));
        }
        else if (userManager.loggedInHasPermission(Perms.CAN_SPEAK_AT_TALK)) {
            subSystems.put(1, subSystemFactory.createEventSubSystem("viewer", eventManager,
                    userManager, eventUI));
        }

        subSystems.put(2, subSystemFactory.createEventSubSystem("retriever", eventManager, userManager,
                    eventUI));

        optionNames = convertSubSystemsToNames(subSystems);
    }


    /**
     * An override of the built-in toString method.
     * @return the string "Events"
     */
    @Override
    public String toString() {
        return "Events";
    }

}
