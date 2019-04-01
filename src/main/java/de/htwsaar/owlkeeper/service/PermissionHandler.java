package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.helper.exceptions.UserInitializationException;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.HasID;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.function.Predicate;

public class PermissionHandler {
    private static PermissionHandler handler;
    private static DeveloperModel user;
    private static String userGroup;

    public static PermissionHandler getPermissionHandler() {
        return handler;
    }

    public static PermissionHandler initialize() {
        handler = new PermissionHandler();
        user = DeveloperManager.getCurrentDeveloper();
        if (user == null)
            throw new UserInitializationException("No current user has been set. Call DeveloperManager.login() first!");
        userGroup = user.getGroup();
        return handler;
    }

    /**
     * Check a static permission described in enum {@link de.htwsaar.owlkeeper.helper.Permissions}, each group is given
     * a set of statically defined permissions.
     *
     * @param action the permission to check
     * @throws InsufficientPermissionsException If the user does not have the requested permission.
     */
    public static boolean checkPermission(final int action) throws InsufficientPermissionsException {
        if (userGroup.equals("admin")) return true;
        if (checkAction(userGroup, action)) {
            return true;
        } else {
            throw new InsufficientPermissionsException("User" + user.getContainer().getEmail() + " of group " + userGroup +
                    " does not have sufficient privileges to execute action " + action);
        }
    }

    /**
     * Check a dynamic permission described by a SQL Query in {@link de.htwsaar.owlkeeper.storage.dao.AccessControlDao}
     * or any other conditional using the currently logged in developer to check permissions.
     *
     * @param predicate The conditional to check as a Predicate. The parameter of this predicate is interpreted by the PermissionHandler
     *                  as the currently authenticated user.
     * @throws InsufficientPermissionsException If the user does not have the requested permission.
     */
    public static boolean checkPermission(final Predicate<Developer> predicate) throws InsufficientPermissionsException {
        if (userGroup.equals("admin")) return true;
        if (predicate.test(user.getContainer())) {
            return true;
        } else {
            throw new InsufficientPermissionsException("User" + user.getContainer().getEmail() + " of group " + userGroup +
                    " does not have sufficient privileges to execute action.");
        }
    }

    private static boolean checkAction(final String group, final int action) {
        int permissions = Permissions.permissionOf(group);
        permissions >>>= (int) (Math.log(action) / Math.log(2));
        return permissions % 2 == 0;
    }

}