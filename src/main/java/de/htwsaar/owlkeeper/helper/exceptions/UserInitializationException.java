package de.htwsaar.owlkeeper.helper.exceptions;

public class UserInitializationException extends RuntimeException {
    public UserInitializationException() {
    }

    public UserInitializationException(String s) {
        super(s);
    }
}