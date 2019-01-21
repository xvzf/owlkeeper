package de.htwsaar.owlkeeper.storage;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Properties;

public class DBConnection {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static Properties config = ConfigurationManager.getConfigManager().getConfig("db");
    private static Jdbi jdbi;

    public static Jdbi getJdbi() {

        if( jdbi == null) {
            // Create connection to the database
            initiateDBConnection();
        }
        return jdbi;
    }

    public static void initiateDBConnection() {
        try {
            jdbi = Jdbi.create(config.getProperty("url"), config);
            jdbi.installPlugin(new SqlObjectPlugin());
            logger.info("Connection to DB established");
        } catch (Exception e) {
            logger.error("Connection to DB could not be established", e);
        }
    }
}
