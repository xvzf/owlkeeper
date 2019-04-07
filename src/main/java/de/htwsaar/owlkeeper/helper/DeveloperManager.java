package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.helper.exceptions.UserInitializationException;
import de.htwsaar.owlkeeper.service.PermissionHandler;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

/**
 * A Manager for login a developer
 */
public class DeveloperManager {

    private static DeveloperModel currUser;

    /**
     * Login for a developer with a given email
     *
     * @param email
     */
    public static void loginDeveloper(String email) {
        currUser = new DeveloperModel(email);
        PermissionHandler.initialize();
    }

    /**
     * Returns the currently logged in Developer
     *
     * @return
     */
    public static DeveloperModel getCurrentDeveloper() {
        if (currUser == null)
            throw new UserInitializationException("No current user has been set. Call DeveloperManager.login() first!");

        return currUser;
    }

    /**
     * Checks the given Login credentials
     * @param emailString user email address
     * @param passwordString user password string
     * @return returns true if the login credentials are correct
     */
    public static boolean checkLogin(String emailString, String passwordString) {
        DeveloperModel model = new DeveloperModel(emailString);
        // User does not exist
        if (model.getContainer() == null) {
            return false;
        }
        try {
            // Compare password hash
            String pwHash = DeveloperModel.getHash(passwordString);
            return pwHash.equals(model.getContainer().getPwhash());
        } catch (Exception e) {
            return false;
        }
    }

    public static Developer getDeveloper() {
        return getCurrentDeveloper().getContainer();
    }

    public static long getCurrentId() {
        return getDeveloper().getId();
    }

    public static String getCurrentEmail() {
        return getDeveloper().getEmail();
    }
}
