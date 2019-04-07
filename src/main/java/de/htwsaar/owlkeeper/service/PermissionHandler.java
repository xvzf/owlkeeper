package de.htwsaar.owlkeeper.service;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.helper.exceptions.UserInitializationException;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.function.Predicate;

public class PermissionHandler {
    private static final String NO_CURRENT_USER_SET = "No current user has been set. Call DeveloperManager.login() first!";
    private static final String USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION = "User %s of group %s does not have sufficient privileges to execute action %s";

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
            throw new UserInitializationException(NO_CURRENT_USER_SET);
        userGroup = user.getGroup();
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
        if (userGroup.equals("admin"))
            return true;
        if (checkAction(userGroup, action)) {
            return true;
        } else {
            throw new InsufficientPermissionsException(String.format(USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION,
                    user.getContainer().getEmail(), userGroup, action));
        }
    }

    /**
     * Checks if the currently logged user is allowed to execute the
     * given permission
     * @param permission permission enum
     * @return boolean value
     */
    public static boolean can(Permissions permission){
        try{
            return checkPermission(permission.get());
        } catch (InsufficientPermissionsException e){
            return false;
        }
    }

    /**
     * Checks if the current developer
     * @param predicate predicate to check developer individual data
     * @return boolean value
     */
    public static boolean fulfills(Predicate<Developer> predicate){
        try{
            return checkPermission(predicate);
        } catch (InsufficientPermissionsException e){
            return false;
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
        if (userGroup.equals("admin"))
            return true;
        if (predicate.test(user.getContainer())) {
            return true;
        } else {
            throw new InsufficientPermissionsException(String.format(USER_OF_GROUP_NO_PRIVILEGES_FOR_ACTION,
                    user.getContainer().getEmail(), userGroup, ""));
        }
    }

    /**
     * Checks, if the given group has the permission.
     *
     * @param group the group to check
     * @param action the action to check
     * @return true if the group has the permissions, false if not.
     */
    private static boolean checkAction(final String group, final int action) {
        int permissions = Permissions.permissionOf(group);
        permissions >>>= (int) (Math.log(action) / Math.log(2));
        return permissions % 2 == 1;
    }

}