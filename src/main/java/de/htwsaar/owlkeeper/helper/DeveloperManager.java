package de.htwsaar.owlkeeper.helper;

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
    }

    /**
     * Returns the currently logged in Developer
     *
     * @return
     */
    public static DeveloperModel getCurrentDeveloper() {
        return currUser;
    }
}