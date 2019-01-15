package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;

/**
 * Factory class providing static methods to edit and change existing configurations.
 */
public class ConfigEditor {

    /**
     * Edits the database configuration entry for the given key to be the new value
     *
     * @param key   the entry to change
     * @param value the value it should now be
     * @throws ConfigurationException when an invalid key is provided.
     */
    public static void editDatabaseConfiguration(DATABASE_CONFIGS key, String value) {
        DatabaseConnectionConfig config = ConfigLoader.loadDatabaseConfiguration();
        System.out.println("Received key" + key);
        switch (key) {
            case URL:
                config.changeUrl(value);
                break;
            case USER:
                config.changeUser(value);
                break;
            case PASSWORD:
                config.changePassword(value);
                break;
            case USE_SSL:
                config.changeUse_ssl(value);
                break;
            default:
                throw new ConfigurationException("Invalid value " + key + " does not exist in config database.");
        }

    }
}
