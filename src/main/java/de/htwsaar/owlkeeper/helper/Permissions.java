package de.htwsaar.owlkeeper.helper;

public enum Permissions {

    CREATE_PROJECT(1), DELETE_PROJECT(2), CREATE_PROJECT_STAGE(4), DELETE_PROJECT_STAGE(8),
    CREATE_TASK(16), VIEW_TASK(32),
    FULFIL_TASK(64), ASSIGN_TEAM_TO_TASK(128),
    CREATE_USER(256), CREATE_TEAM(512), DISSOLVE_TEAM(1024), //TODO More static permissions

    PROJECT_OWNER_PERMISSIONS(CREATE_PROJECT.get() + DELETE_PROJECT.get() + CREATE_PROJECT_STAGE.get() + DELETE_PROJECT_STAGE.get() + ASSIGN_TEAM_TO_TASK.get() +
            CREATE_USER.get() + CREATE_TEAM.get() + DISSOLVE_TEAM.get()),

    DEVELOPER_PERMISSIONS(CREATE_TASK.get() + VIEW_TASK.get() + CREATE_USER.get());

    private int val;

    Permissions(int val) {
        this.val = val;
    }

    /**
     * Retrieves the static permissions for a given group
     *
     * @param group the group to retrieve the permissions for.
     * @return
     */
    public static int permissionOf(final String group) {
        if (group.equals("project")) {
            return PROJECT_OWNER_PERMISSIONS.get();
        } else if (group.equals("task")) {
            return DEVELOPER_PERMISSIONS.get();
        } else {
            return 0;
        }

    }

    public int get() {
        return val;
    }
}