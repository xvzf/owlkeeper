package de.htwsaar.owlkeeper.storage.db;

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

    private static final String url;
    private static final Properties props = new Properties();
    // @TODO load configuration out of config file or environment
    static {
        url = "jdbc:postgresql://localhost:5432/owlkeeper";
        props.setProperty("user", "owlkeeper");
        props.setProperty("password", "owlkeeper");
        props.setProperty("use_ssl", "true");
    }

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
        return DriverManager.getConnection(url, props);
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