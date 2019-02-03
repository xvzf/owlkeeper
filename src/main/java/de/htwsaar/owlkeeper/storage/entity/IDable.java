package de.htwsaar.owlkeeper.storage.entity;

/**
 * Interface for classes able to be saved in the database, thus requiring an id
 * TODO: Find a better name for the interface
 */
public interface IDable {
    long getId();

    void setId(long newId);
}
