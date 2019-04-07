package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.helper.exceptions.UserInitializationException;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.List;
import java.util.function.Predicate;

public class PermissionHandler {
    private static final String NO_CURRENT_USER_SET = "No current user has been set. Call DeveloperManager.login() first!";
    private static final String USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION = "User %s of group %s does not have sufficient privileges to execute action %s";

    private static PermissionHandler handler;
    private static DeveloperModel user;
    private static List<String> userGroups;

    public static PermissionHandler getPermissionHandler() {
        return handler;
    }

    public static PermissionHandler initialize() {
        handler = new PermissionHandler();
        user = DeveloperManager.getCurrentDeveloper();
        if (user == null)
            throw new UserInitializationException(NO_CURRENT_USER_SET);
        userGroups = user.getGroup();
        return handler;
    }

    /**
     * Check a static permission described in enum
     * {@link de.htwsaar.owlkeeper.helper.Permissions}, each group is given a set of
     * statically defined permissions. Static permissions are implemented using
     * bitwise permissions.
     *
     * @param action the permission to check
     * @throws InsufficientPermissionsException If the user does not have the
     *             requested permission.
     */
    public static boolean checkPermission(final int action) throws InsufficientPermissionsException {
        if (userGroups.contains("admin"))
            return true;
        if (checkAction(userGroups, action)) {
            return true;
        } else {
            throw new InsufficientPermissionsException(String.format(USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION,
                    user.getContainer().getEmail(), userGroups, action));
        }
    }

    /**
     * Check a dynamic permission described by a SQL Query in
     * {@link de.htwsaar.owlkeeper.storage.dao.AccessControlDao} or any other
     * conditional using the currently logged in developer to check permissions.
     *
     * @param predicate The conditional to check as a Predicate. The parameter of
     *            this predicate is interpreted by the PermissionHandler as the
     *            currently authenticated user.
     * @throws InsufficientPermissionsException If the user does not have the
     *             requested permission.
     */
    public static boolean checkPermission(final Predicate<Developer> predicate)
            throws InsufficientPermissionsException {
        if (userGroups.contains("admin"))
            return true;
        if (predicate.test(user.getContainer())) {
            return true;
        } else {
            throw new InsufficientPermissionsException(String.format(USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION,
                    user.getContainer().getEmail(), userGroups, ""));
        }
    }

    /**
     * Checks, if the given group has the permission.
     *
     * @param groups the group to check
     * @param action the action to check
     * @return true if the group has the permissions, false if not.
     */
    private static boolean checkAction(final List<String> groups, final int action) {
        int permissions = groups.stream().map(Permissions::permissionOf).reduce(0, (x,y) -> x | y);
        permissions >>>= (int) (Math.log(action) / Math.log(2));
        return permissions % 2 == 1;
    }

}