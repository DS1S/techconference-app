package backend.systems.usermangement.managers;

import backend.entities.users.Perms;
import backend.entities.users.Socials;
import backend.entities.users.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a UserManager, handles the management of a collection of users.
 * Performs basic retrieval and application to data contained in the users it manages.
 */
public class UserManager implements Serializable {
    private Map<UUID, User> users;
    private User loggedInUser;

    /**
     * Constructs an empty UserManager.
     */
    public UserManager() {
        this.users = new HashMap<>();
    }

    /**
     * Add a user into the collection of users for <this> to handle.
     * Ensures a user is not already in the manager by checking the User's UUID.
     * @param type The type of the user: "attendee", "speaker", "organizer".
     * @param username The username of the user.
     * @param password The password of the user.
     * @param name The full name of the User.
     */
    public void addUser(String type, String username, String password, String name) {
        UserFactory userCreator = new UserFactory();
        User u;

        do {
            u = userCreator.buildUser(type, name, username, password);
        }while(containsUserWithUUID(u.getUUID()));

        this.users.put(u.getUUID(), u);
    }

    /**
     * Check the UserManager if it contains a user with a particular UUID.
     * @param id The UUID of the User to check for.
     * @return True IFF the User is found.
     */
    public boolean containsUserWithUUID(UUID id) {
        return users.containsKey(id);
    }

    /**
     * Returns True IFF the UserManager contains a user with a username <username>
     * @param username The username of a  potential User.
     * @return True IFF the user with that Username exists. (Usernames are unique)
     */
    public boolean containsUserWithUsername(String username) {
        for (User u : this.users.values()) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks a User's password, validating it.
     * @param id The UUID of the User to check.
     * @param password The password to check with that UUID.
     * @return True IFF the user with UUID id has that password.
     */
    public boolean checkPasswordWithUUID(UUID id, String password) {
        return users.get(id).checkPassword(password);
    }

    /**
     * Checks if the user corresponding to a UUID is banned.
     * @param id the UUID of the user to check
     * @return true if the user is banned, false otherwise
     */
    public boolean checkBannedWithUUID(UUID id) {return users.get(id).getIsBanned();}

    /**
     * Bans a user.
     * @param id the UUID of the user to ban
     * @param banned the banned status of the user
     */
    public void setUserBan(UUID id, boolean banned) {
        users.get(id).setBanned(banned);
    }

    /**
     * Gets the UUID of a user with a username <username>
     * @param username The Username of the user.
     * @return The UUID of the user with a username <username>
     */
    public UUID getUUIDWithUsername(String username) {
        for (User u : this.users.values()) {
            if (u.getUsername().equals(username)) {
                return u.getUUID();
            }
        }
        return null;
    }

    /**
     * Gets the logged in User's UUID
     * @return Returns the UUID of the logged in User.
     */
    public UUID getLoggedInUserUUID() {
        return loggedInUser.getUUID();
    }

    /**
     * Sets the logged in the user.
     * @param userID The UUID of the user to set as logged in.
     */
    public void setLoggedInUser(UUID userID) {
        this.loggedInUser = this.users.get(userID);
        users.get(userID).setLastLoggedIn(LocalDateTime.now());
    }

    /**
     * Checks if the logged in user has a permission <permission>
     * @param permission Permission Key to check the user has.
     * @return True IFF the user has the permission.
     */
    public boolean loggedInHasPermission(Perms permission) {
        return this.loggedInUser.getPermissions().get(permission);
    }

    /**
     * Get the username of a user with a UUID
     * @param userUUID The UUID of the User.
     * @return Return the Username of the User.
     */
    public String getUsernameWithUUID(UUID userUUID) {
        return users.get(userUUID).getUsername();
    }

    /**
     * Get the name of a user with a UUID
     * @param userUUID The UUID of the User.
     * @return Return the name of the User.
     */
    public String getNameWithUUID(UUID userUUID) {
        return users.get(userUUID).getName();
    }

    /**
     * Check if the a user with UUID userID as permission key <permission>
     * @param userID The UUID of the User to check.
     * @param permission The permission key to check.
     * @return True IFF the User has the permission.
     */
    public boolean hasPermission(UUID userID, Perms permission) {
        return this.users.get(userID).getPermissions().get(permission);
    }

    /**
     * Gets all the stored User UUID's.
     * @return List of UUID stored in UserManager.
     */
    public List<UUID> getUUIDs() {
        return new ArrayList<>(this.users.keySet());
    }

    /**
     * Check if a User has logged in between the time stated
     * @param userID The UUID of the User to check.
     * @param StartTime The earliest time someone has logged in
     * @param EndTime The latest time someone has logged in
     * @return Boolean True if User has logged in between StartTime and EndTime inclusively
     */
    public boolean checkUserLoggedIn(UUID userID, LocalDateTime StartTime, LocalDateTime EndTime) {
        LocalDateTime time = this.users.get(userID).getLastLoggedIn();
        return StartTime.isBefore(time) && EndTime.isAfter(time);
    }

    /**
     * Checks if a user has other social media links.
     * @param uuid UUID of the user
     * @return true if the user has at least one social media link, false otherwise
     */
    public boolean userHasLinks(UUID uuid) {
        return getUserLinks(uuid).size() > 0;
    }

    /**
     * Gets a list of a user's social media links.
     * @param uuid UUID of the user
     * @return an empty list if the user does not have any links, otherwise returns a list of their links
     */
    public List<String> getUserLinks(UUID uuid) {
        return users.get(uuid).getProfileLinks();
    }

    /**
     * Removes a link to a particular social media platform from a User's profile.
     * @param uuid The UUID of the User whose link is going to be removed.
     * @param social The social media platform of the link to be removed.
     */
    public void removeUserLink(UUID uuid, Socials social) {
        users.get(uuid).removeProfileLink(social);
    }

    /**
     * Sets a logged in user's link to a specific social media platform.
     * @param social the social media platform to link to
     * @param link the link to the platform
     */
    public void setLoggedInUserLink(Socials social, String link) {
        loggedInUser.setProfileLink(social, link);
    }
}
