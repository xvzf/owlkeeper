package de.htwsaar.owlkeeper.helper;


/**
 * Enum describing all permissions in the program.
 * Eg allowed to delete a project or not allowed to
 * Complex permission are build via bitfield.
 */
public enum Permissions {

    CREATE_PROJECT(1), DELETE_PROJECT(2),
    CREATE_PROJECT_STAGE(4), DELETE_PROJECT_STAGE(8),
    CREATE_TASK(16), VIEW_TASK(32), DELETE_TASK(64),
    ASSIGN_TEAM_TO_TASK(128),
    CREATE_USER(256),
    CREATE_TEAM(512), DISSOLVE_TEAM(1024),

    PROJECT_OWNER_PERMISSIONS(
            CREATE_PROJECT.get() +
                    DELETE_PROJECT.get() +
                    CREATE_PROJECT_STAGE.get() +
                    DELETE_PROJECT_STAGE.get() +
                    ASSIGN_TEAM_TO_TASK.get() +
                    CREATE_USER.get() +
                    CREATE_TEAM.get() +
                    DISSOLVE_TEAM.get()
    ),

    DEVELOPER_PERMISSIONS(CREATE_TASK.get() + VIEW_TASK.get() + CREATE_USER.get());

    private int bitfield;

    private static final String GROUP_STRING_PROJECT = "project";
    private static final String GROUP_STRING_TASK = "task";

    /**
     * Constructor
     *
     * @param bitfield the bitfield describing the permissions
     */
    Permissions(int bitfield) {
        this.bitfield = bitfield;
    }

    /**
     * Retrieves the static permissions for a given group
     *
     * @param group the group to retrieve the permissions for.
     * @return
     */
    public static int permissionOf(final String group) {
        if (group.equals(GROUP_STRING_PROJECT)) {
            return PROJECT_OWNER_PERMISSIONS.get();
        } else if (group.equals(GROUP_STRING_TASK)) {
            return DEVELOPER_PERMISSIONS.get();
        } else {
            return 0;
        }

    }

    /**
     * Getter for the bitfield
     *
     * @return bitfield
     */
    public int get() {
        return bitfield;
    }
}