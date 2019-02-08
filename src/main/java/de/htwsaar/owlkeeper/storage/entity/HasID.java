package de.htwsaar.owlkeeper.storage.entity;

/**
 * Interface for classes able to be saved in the database, thus requiring an id
 */
public interface HasID{
    long getId();

    void setId(long newId);
}
