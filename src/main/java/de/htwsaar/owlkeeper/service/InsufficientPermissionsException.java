package de.htwsaar.owlkeeper.service;

public class InsufficientPermissionsException extends Exception {
    public InsufficientPermissionsException() {
    }

    public InsufficientPermissionsException(String s) {
        super(s);
    }
}
