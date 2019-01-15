package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants;
import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;
import de.htwsaar.owlkeeper.storage.db.DBConnection;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ConfigLoader {

    /**
     * Loads or, if it doesn't yet exist, creates, a config object for a databaseConnec
     *
     * @return
     */
    public static DatabaseConnectionConfig loadDatabaseConfiguration() {
        Preferences prefs = Preferences.userNodeForPackage(DBConnection.class);

        try {
            // If there are no keys/value pairs in this store yet we need to create them.
            if (prefs.keys().length == 0) {
                System.out.println("Preference file for DBConnection.class didn't exist.");
                prefs.put(DATABASE_CONFIGS.URL.toString(), DatabaseConstants.DEFAULT_DATABASE_URL);
                prefs.put(DATABASE_CONFIGS.USER.toString(), DatabaseConstants.DEFAULT_DATABASE_USER);
                prefs.put(DATABASE_CONFIGS.PASSWORD.toString(), DatabaseConstants.DEFAULT_DATABASE_PASSWORD);
                prefs.put(DATABASE_CONFIGS.USE_SSL.toString(), DatabaseConstants.DEFAULT_DATABASE_USE_SSL);
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        return new DatabaseConnectionConfig(prefs);
    }

}
