package de.htwsaar.owlkeeper.storage.db;

import de.htwsaar.owlkeeper.helper.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBConnection
 */
public class DBConnection {
    private static Logger logger = LogManager.getLogger(DBConnection.class);

    // The default connection to the Database
    protected static Connection currentConnection = null;

    private static Properties config = Resource.getProperties("/owlkeeper.properties");

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
        return DriverManager.getConnection(config.getProperty("url"), config);
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

}