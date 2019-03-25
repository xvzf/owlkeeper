package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.helper.permissions.PermissionObserver;

public class PermissionHandler implements PermissionObserver {
    private static PermissionHandler handler;
    private String userGroup;

    private PermissionHandler(final String userGroup) {
        this.userGroup = userGroup;

    }

    public static PermissionHandler getPermissionHandler() {
        return handler;
    }

    public static PermissionHandler initialize(final String userGroup) {
        handler = new PermissionHandler(userGroup);
        return handler;
    }

    public void changePermissionGroup(final String userGroup) {
        this.userGroup = userGroup;
    }


    @Override
    public void update(int action) throws InsufficientPermissionsException {

        if (!checkAction(action)) {
            throw new InsufficientPermissionsException("User of group " + userGroup + " does not have sufficient privileges to execute action " + action);
        }
    }

    private boolean checkAction(final int action) {
        return false; //TODO
    }
}
