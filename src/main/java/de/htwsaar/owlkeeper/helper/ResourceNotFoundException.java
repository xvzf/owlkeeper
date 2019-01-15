package de.htwsaar.owlkeeper.helper;

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