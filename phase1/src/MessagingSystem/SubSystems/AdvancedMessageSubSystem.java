package MessagingSystem.SubSystems;

import CoreEntities.Users.Perms;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An AdvancedMessageSubSystem that extends MessageSubSystem.
 */
public class AdvancedMessageSubSystem extends MessageSubSystem {

    /**
     * Constructs a new instance of AdvancedMessageSubSystem using the given parameters.
     * @param userManager the user manager used by the system
     * @param messageManager the message manager used by the system
     * @param numOptions the number of options available to the user on the menu
     */
    public AdvancedMessageSubSystem(UserManager userManager, MessageManager messageManager, int numOptions) {
        super(userManager, messageManager, numOptions);
    }

    @Override
    protected void displayOptions() {
        inboxUI.displaySchedulerMenuOptions();
    }

    @Override
    protected void processMainSignInput(int index) {
        processBaseInput(index);
        switch (index){
            case(3):
                processMessageAllAttendees();
                break;
            case(4):
                processMessageAllSpeakers();
                break;
        }
    }

    private void processMessageAllAttendees(){
        String message = processMessageBody();
        messageUserWithPerm(message, Perms.canSpeakAtTalk);
    }

    private void processMessageAllSpeakers(){
        String message = processMessageBody();
        messageUserWithPerm(message, Perms.canSignUpEvent);
    }

    private void messageUserWithPerm(String message, Perms perm){
        List<UUID> userUUIDs = userManager.getUUIDs();
        List<UUID> targetUUIDs = new ArrayList<>();
        for (UUID id : userUUIDs) {
            if (userManager.hasPermission(id, perm)) {
                targetUUIDs.add(id);
            }
        }
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), targetUUIDs, message);
        inboxUI.sentPrompt();
    }
}