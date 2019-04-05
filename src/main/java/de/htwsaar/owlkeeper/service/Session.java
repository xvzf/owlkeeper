package de.htwsaar.owlkeeper.service;

/**
 * Program sessions that represents the main handler for each launched instance
 * of the software.
 */
public class Session {

    public Session() {
        PermissionHandler.initialize();
    }

    public static void main(String[] args) {
        new Session();
    }

}
