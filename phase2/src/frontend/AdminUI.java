package frontend;

import java.util.ArrayList;
import java.util.List;

public class AdminUI extends MenuUI{
    public void displayAdminOptions(boolean[] perms){
        displayIndexPrompt();
        displayOptions(getOptions(perms));
    }

    public void displayBanOptions(){
        ArrayList<String> options = new ArrayList<>(){
            {
                add("Unban user");
                add("Ban user");
            }
        };

        displayIndexPrompt();
        displayOptions(options);
    }

    public void promptUserName(){
        System.out.println("Please enter the name of the user:");
    }

    public void displayInvalidUser(){
        System.out.println("A user by that name was not found!");
    }

    public void displayBanSuccess(boolean banned, String username){
        System.out.println(username +  " has been successfully " + (banned?"banned":"unbanned"));
    }

    public void displayMessageViewOptions(){

    }

    private List<String> getOptions(boolean[] perms){
        final int CAN_VIEW_STATS = 0;
        final int CAN_BAN_USERS = 1;
        final int CAN_SEE_ALL_MESSAGES = 2;

        List<String> options = new ArrayList<>();

        if (perms[CAN_VIEW_STATS]){
            options.add("View Statistics");
        }
        if (perms[CAN_BAN_USERS]){
            options.add("Ban a User");
        }
        if (perms[CAN_SEE_ALL_MESSAGES]) {
            options.add("View a User's Messages");
        }
        return options;
    }
}
