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
    private static DBConnection instance;
    private static Properties config = Resource.getProperties("/owlkeeper.properties");

    public static DBConnection getInstance() throws SQLException {
        // Check if instance is null or SQL Connection is null
        if (instance == null || instance.getSQLConnection() == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Connection
    private  Connection conn;

    private DBConnection() throws SQLException {
        logger.info("Instantiating new connection to the database");
        this.conn = DriverManager.getConnection(config.getProperty("url"), config);
    }

    /**
     * Creates a new database connection
     *
     * !! you have to close the connection when done
     *
     * @return New connection to the Database
     * @throws SQLException
     */
    public static DBConnection getNewConnection() throws SQLException {
        return new DBConnection();
    }

    /**
     * Closes the default database connection
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            logger.info("Closed default database connection");
        } catch (SQLException se) {
            logger.error("Could not close Database connection", se);
        }
        conn = null;
    }

    public Connection getSQLConnection() {
        return conn;
    }

}