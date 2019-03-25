package de.htwsaar.owlkeeper.storage.entity;

import de.htwsaar.owlkeeper.helper.permissions.PermissionObservable;

/**
 * Interface for classes able to be saved in the database, thus requiring an id
 */
public abstract class HasID extends PermissionObservable {
    public abstract long getId();

    public abstract void setId(long newId);
}
