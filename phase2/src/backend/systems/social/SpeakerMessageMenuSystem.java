package backend.systems.social;

import backend.entities.users.Perms;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.social.managers.MessageManager;
import utility.inputprocessors.InputProcessor;
import utility.inputprocessors.OptionInputProcessor;

import java.util.*;

/**
 * An extension of MessageMenuSystem responsible for the message menu available to speakers.
 */
class SpeakerMessageMenuSystem extends MessageMenuSystem {
    private List<EventManager> eventManagers;

    /**
     * Creates a SpeakerMessageMenuSystem object.
     * @param userManager A UserManager object that is already instantiated at the point this is instantiated.
     * @param messageManager A MessageManager object that is already instantiated at the point this is instantiated.
     * @param eventManagers A EventManager object that is already instantiated at the point this is instantiated.
     */
    public SpeakerMessageMenuSystem(UserManager userManager, MessageManager messageManager,
                                    List<EventManager> eventManagers) {
        super(userManager, messageManager, 7);
        this.eventManagers = eventManagers;
    }

    /**
     * Tells the inbox UI to display options in the speaker messaging page.
     */
    @Override
    protected void displayOptions() {
        inboxUI.displayTalkSpeakerMenuOptions();
    }

    /**
     * Processes an integer input in the speaker's messaging page.
     * @param index The input to be processed. 1 allows for displaying all messages to the speaker.
     *              2 allows for replying to an attendee. 3 allows for making an announcement to the
     *              speaker's talk(s).
     */
    @Override
    protected void processInput(int index) {
        switch (index) {
            case(1):
                processBaseInput(1);
                break;
            case(2):
                processBaseInput(2);
                break;
            case(3):
                processBaseInput(4);
                break;
            case(4):
                processBaseInput(5);
                break;
            case(5):
                replyToAttendee();
                break;
            case(6):
                processMessageToTalks();
                break;
        }
    }

    private void processMessageToTalks() {
        List<Map<String, Object>> eventsData = new ArrayList<>();
        for (EventManager eventManager: eventManagers){
            eventsData.addAll(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
        }

        if (eventsData.isEmpty()) {
            inboxUI.displayError("You aren't hosting any talks!");
        }
        else{
            inboxUI.talksPrompt();
            String talks = askForString("Talk(s)");
            String message = processMessageBody();
            String title = processTitle();
            sendMessageToTalks(Arrays.asList(talks.split(",")), message, title);
        }
    }

    private void sendMessageToTalks(List<String> events, String msg, String title) {
        List<UUID> attendeeUUIDs = new ArrayList<>();
        for (String event : events) {
            List<UUID> eventUUIDs = new ArrayList<>();
            for(EventManager eventManager: eventManagers){
                eventManager.retrieveAttendees(event, userManager.getLoggedInUserUUID());
            }
            attendeeUUIDs.addAll(eventUUIDs);
        }
        if (attendeeUUIDs.isEmpty()) inboxUI.displayError("No one is attending your talks!");
        else inboxUI.sentPrompt();
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), attendeeUUIDs, msg, title);
    }

    private void replyToAttendee() {
        List<Map<String, Object>> messagesData = messageManager.getInboxData(userManager.getLoggedInUserUUID());
        int index = processMessages(messagesData) - 1;

        if(index != -1) {
            UUID replierUUID = (UUID)messagesData.get(index).get("sender");
            if(userManager.hasPermission(replierUUID, Perms.CAN_BE_MESSAGED)) {
                String message = processMessageBody();
                String title = processTitle();
                messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(), replierUUID, message, title);
                inboxUI.sentPrompt();
            }
            else{
                inboxUI.displayError("That User cannot be messaged.");
            }
        }
    }

    private int processMessages(List<Map<String, Object>> messagesData) {
        inboxUI.displayInbox(messagesData);
        if(!messagesData.isEmpty()) {
            InputProcessor<Integer> optionInputProcessor = new OptionInputProcessor(input, messagesData.size());
            return optionInputProcessor.processInput();
        }
        return 0;
    }

    /**
     * Returns the string representation of this system.
     * @return The string "Speaker Inbox/Messaging".
     */
    @Override
    public String toString() { return "Speaker Inbox/Messaging"; }
}