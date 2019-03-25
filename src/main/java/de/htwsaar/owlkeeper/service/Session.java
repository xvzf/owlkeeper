package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.Base64;

/**
 * Program sessions that represents the main handler for each launched instance of the software.
 */
public class Session {
    // Field that will always contain the user ob this session
    private final DeveloperModel sessionUser = new DeveloperModel(1); //TODO


    public Session() {
        System.out.println(sessionUser);
        System.out.println("test: "+sessionUser.getGroup());
    }

    public static void main(String[] args) {
        new Session();
    }


}
