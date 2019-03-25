package de.htwsaar.owlkeeper.helper.permissions;

import de.htwsaar.owlkeeper.service.InsufficientPermissionsException;

public interface PermissionObserver {
    void update(int action) throws InsufficientPermissionsException;
}
