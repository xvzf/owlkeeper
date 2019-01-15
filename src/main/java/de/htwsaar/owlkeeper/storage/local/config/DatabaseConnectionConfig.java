package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;

import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * Acts as a simple data container to provide the data stored in the preference file.
 */
public class DatabaseConnectionConfig implements Config {
    private final Preferences preferences;

    public DatabaseConnectionConfig(final Preferences preferences) {
        this.preferences = preferences;
    }

    public String getUrl() {
        return preferences.get(DATABASE_CONFIGS.URL.toString(), null);
    }

    public void changeUrl(final String url) {
        preferences.put(DATABASE_CONFIGS.URL.toString(), url);
    }

    public String getUser() {
        return preferences.get(DATABASE_CONFIGS.USER.toString(), null);
    }

    public void changeUser(final String user) {
        preferences.put(DATABASE_CONFIGS.USER.toString(), user);
    }

    public String getPassword() {
        return preferences.get(DATABASE_CONFIGS.PASSWORD.toString(), null);
    }

    public void changePassword(final String password) {
        preferences.put(DATABASE_CONFIGS.PASSWORD.toString(), password);
    }

    public String getUse_ssl() {
        return preferences.get(DATABASE_CONFIGS.USE_SSL.toString(), null);
    }

    public void changeUse_ssl(final String flag) {
        if (flag.equals("true") || flag.equals("false")) {
            preferences.put(DATABASE_CONFIGS.USE_SSL.toString(), flag);
        }
    }

    private Preferences getPreferences() {
        return preferences;
    }

    /**
     * Turns the Preferences into a Java Properties Map to enable connection to the database.
     *
     * @return A Properties Object containing connection information.
     */
    public Properties connectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", getUser());
        props.setProperty("password", getPassword());
        props.setProperty("use_ssl", getUse_ssl());

        return props;
    }
}
