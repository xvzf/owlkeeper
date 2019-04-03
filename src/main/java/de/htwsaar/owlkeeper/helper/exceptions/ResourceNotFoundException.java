package de.htwsaar.owlkeeper.helper.exceptions;

import java.io.FileNotFoundException;

/**
 * ResourceNotFoundException
 */
public class ResourceNotFoundException extends FileNotFoundException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String s) {
        super(s);
    }
}