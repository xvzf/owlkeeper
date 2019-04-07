package de.htwsaar.owlkeeper.helper.exceptions;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException() {
    }

    public InsufficientPermissionsException(String s) {
        super(s);
    }
}
