package de.htwsaar.owlkeeper.storage;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Properties;

public class DBConnection {
    private static final String URL_CONFIG_SECTION = "db";
    private static final String URL_CONFIG_KEY = "url";
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static Properties config = ConfigurationManager.getConfigManager().getConfig(URL_CONFIG_SECTION);
    private static Jdbi jdbi;

    public static Jdbi getJdbi() {

        if (jdbi == null) {
            // Create connection to the database
            initiateDBConnection();
        }
        return jdbi;
    }

    public static void initiateDBConnection() {
        String url;
        try {
            url = config.getProperty(URL_CONFIG_KEY);
        } catch (Exception e) {
            logger.error("Could not find key \"" + URL_CONFIG_KEY +
                    "\" in section \"" + URL_CONFIG_SECTION + "\" in config file", e);
            throw e;
        }
        try {
            jdbi = Jdbi.create(url, config);
            jdbi.installPlugin(new SqlObjectPlugin());
            // open and close a connection to check if it's established
            jdbi.open().close();
            logger.info("Connection to DB at " + url + " established");
        } catch (Exception e) {
            logger.error("Connection to DB at " + url + " could not be established", e);
        }
    }
}
