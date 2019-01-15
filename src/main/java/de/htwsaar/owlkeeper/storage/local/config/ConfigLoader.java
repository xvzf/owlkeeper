package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants;
import de.htwsaar.owlkeeper.storage.db.DBConnection;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ConfigLoader {

    public static DatabaseConnectionConfig loadDatabaseConnection() {
        Preferences prefs = Preferences.userNodeForPackage(DBConnection.class);

        try {
            // If there are no keys/value pairs in this store yet we need to create them.
            if (prefs.keys().length == 0) {
                prefs.put("url", DatabaseConstants.DEFAULT_DATABASE_URL);
                prefs.put("user",DatabaseConstants.DEFAULT_DATABASE_USER);
                prefs.put("password", DatabaseConstants.DEFAULT_DATABASE_PASSWORD);
                prefs.put("use_ssl", DatabaseConstants.DEFAULT_DATABASE_USE_SSL);
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        return new DatabaseConnectionConfig(prefs);
    }

}
