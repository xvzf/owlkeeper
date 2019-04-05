package de.htwsaar.owlkeeper.storage;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Properties;

/**
 * Class for initiating and getting connections to the database
 * Parameters are defined in the owlkeeper.properties file
 */
public class DBConnection {
    private static final String URL_CONFIG_SECTION = "db";
    private static final String URL_CONFIG_KEY = "url";
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static Properties config = ConfigurationManager.getConfigManager().getConfig(URL_CONFIG_SECTION);
    private static Jdbi jdbi;

    private static final String LOGGER_ERROR_FORMAT_NO_KEY =
            "Could not find key \"%s\" in section \"%s\" in config file";
    private static final String LOGGER_INFO_FORMAT_CONNECTION_OK = "Connection to DB at %s established";
    private static final String LOGGER_ERROR_FORMAT_CONNECTION_NOT_OK =
            "Connection to DB at %s could not be established";

    /**
     * Get a connection to the database.
     * If non is initiated, a new one will be initiated
     *
     * @return a connection to the database
     */
    public static Jdbi getJdbi() {

        if (jdbi == null) {
            // Create connection to the database
            initiateDBConnection();
        }
        return jdbi;
    }

    /**
     * Initiate a connection to the database
     */
    public static void initiateDBConnection() {
        String url;
        try {
            url = config.getProperty(URL_CONFIG_KEY);
        } catch (Exception e) {
            logger.error(String.format(LOGGER_ERROR_FORMAT_NO_KEY, URL_CONFIG_KEY, URL_CONFIG_SECTION), e);
            throw e;
        }
        try {
            jdbi = Jdbi.create(url, config);
            jdbi.installPlugin(new SqlObjectPlugin());
            // open and close a connection to check if it's established
            jdbi.open().close();
            logger.info(String.format(LOGGER_INFO_FORMAT_CONNECTION_OK, url));
        } catch (Exception e) {
            logger.error(String.format(LOGGER_ERROR_FORMAT_CONNECTION_NOT_OK, url), e);
        }
    }
}
