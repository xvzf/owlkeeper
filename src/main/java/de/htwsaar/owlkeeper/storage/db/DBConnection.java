package de.htwsaar.owlkeeper.storage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBConnection
 */
public class DBConnection {
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
            currentConnection = DriverManager.getConnection(url, props);
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
        } catch (SQLException se) {
            // LOG @TODO
        }
        currentConnection = null;
    }

}