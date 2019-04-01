package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.helper.exceptions.UserInitializationException;
import de.htwsaar.owlkeeper.service.PermissionHandler;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

/**
 *
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

}
