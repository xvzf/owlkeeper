package de.htwsaar.owlkeeper.storage.db;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;
import de.htwsaar.owlkeeper.storage.local.config.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 */
public class DBConnection implements Configurable {
    private static Logger logger = LogManager.getLogger(DBConnection.class);

    // The default connection to the Database
    protected static Connection currentConnection = null;

    private static DatabaseConnectionConfig config = ConfigLoader.loadDatabaseConfiguration();

    /**
     * Returns the default Database connection
     *
     * @return Defaut database connectiogetConnectionn
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (currentConnection == null) {
            currentConnection = DBConnection.getNewConnection();
        }

        return currentConnection;
    }

    /**
     * Creates a new database connection
     *
     * !! you have to close the connection when done
     *
     * @return New connection to the Database
     * @throws SQLException
     */
    public static Connection getNewConnection() throws SQLException {
        logger.info("Instantiating new connection to the database");
        return DriverManager.getConnection(config.getUrl(), config.connectionProperties());
    }

    /**
     * Closes the default database connection
     */
    public static void closeConnection() {
        try {
            if (currentConnection != null) {
                currentConnection.close();
            }
            logger.info("Closed default database connection");
        } catch (SQLException se) {
            logger.error("Could not close Database connection", se);
        }
        currentConnection = null;
    }
    // Configurable implementations.

    @Override
    public Config getConfiguration() {
        return config;
    }

    @Override
    public void editConfiguration(DATABASE_CONFIGS key, String value) {
        ConfigEditor.editDatabaseConfiguration(key, value);
    }
}