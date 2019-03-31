package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

/**
 * Program sessions that represents the main handler for each launched instance of the software.
 */
public class Session {
    // Field that will always contain the user of this session
    private final DeveloperModel sessionUser = DeveloperManager.getCurrentDeveloper();

    public Session() {
        PermissionHandler.initialize();
    }

    public static void main(String[] args) {
        new Session();
    }


}
