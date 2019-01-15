package de.htwsaar.owlkeeper.helper.constants;

public class DatabaseConstants {
    public enum DATABASE_CONFIGS {URL, USER, PASSWORD, USE_SSL}

    ;

    public final static String DEFAULT_DATABASE_URL = "jdbc:postgresql://localhost:5432/owlkeeper";
    public final static String DEFAULT_DATABASE_USER = "owlkeeper";
    public final static String DEFAULT_DATABASE_PASSWORD = "owlkeeper";
    public final static String DEFAULT_DATABASE_USE_SSL = "true";
}
