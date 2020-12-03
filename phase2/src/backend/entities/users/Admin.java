package backend.entities.users;

import java.time.LocalDateTime;

public class Admin extends User{

    public Admin(String name, String username, String password, LocalDateTime lastLoggedIn){
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    @Override
    protected void setPermissions() {
        permissions.put(PERMS.canBeMessaged, false);
        permissions.put(PERMS.canMessageTalk, false);
        permissions.put(PERMS.canSchedule, true);
        permissions.put(PERMS.canSignUpEvent, false);
        permissions.put(PERMS.canSignUpUser, true);
        permissions.put(PERMS.canSpeakAtTalk, false);
        permissions.put(PERMS.canViewStats, true);
        permissions.put(PERMS.canBanUsers, true);
        permissions.put(PERMS.canSeeAllMessages, true);
    }
}