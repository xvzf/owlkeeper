package de.htwsaar.owlkeeper.helper;

public enum Permissions {

    CREATE_PROJECT(1), DELETE_PROJECT(2),
    CREATE_TASK(4), VIEW_TASK(8),
    FULFIL_TASK(16), ASSIGN_TEAM_TO_TASK(32),
    CREATE_USER(64), CREATE_TEAM(128), ADD_DEVELOPER_TO_TEAM(256);

    private int val;

    Permissions(int val) {
        this.val = val;
    }

    public int get() {
        return val;
    }
}