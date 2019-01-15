package de.htwsaar.owlkeeper.storage.local.config;

import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * Acts as a simple data container to provide the data stored in the preference file.
 */
public class DatabaseConnectionConfig implements Config {
    private final Preferences preferences;

    public DatabaseConnectionConfig(Preferences preferences) {
        this.preferences = preferences;
    }

    public String getUrl() {
        return preferences.get("url", null);
    }

    public String getUser() {
        return preferences.get("user", null);
    }

    public String getPassword() {
        return preferences.get("password", null);
    }

    public String getUse_ssl() {
        return preferences.get("use_ssl", null);
    }

    private Preferences getPreferences() {
        return preferences;
    }


    /**
     * Turns the Preferences into a Java Properties Map to enable conection to the database.
     * @return
     */
    public Properties connectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", getUser());
        props.setProperty("password", getPassword());
        props.setProperty("use_ssl", getUse_ssl());

        return props;
    }
}
