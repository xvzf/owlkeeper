package de.htwsaar.owlkeeper.storage.entity;

/**
 * Abstract class able to be saved in the database, thus requiring an id
 */
public abstract class HasID {
    public abstract long getId();

    public abstract void setId(long newId);
}
