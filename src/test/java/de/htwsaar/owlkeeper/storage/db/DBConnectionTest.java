package de.htwsaar.owlkeeper.storage.db;

import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DBConnectionTest
 *
 * !!! Docker based PostgreSQL needs to be up and running !!!
 *
 */
public class DBConnectionTest {

    @AfterEach
    public void cleanUp() throws SQLException {
        DBConnection.getInstance().closeConnection();
    }

    /**
     * Test if the framework setups a connection to the running database
     *
     * @throws SQLException
     */
    @Test
    public void testGetConnection() throws SQLException {
        assertNotNull(DBConnection.getInstance());
    }

    /**
     * Tests if the default connection is setup only once
     *
     * @throws SQLException
     */
    @Test
    public void testMultipleDefaultConnectionsEqual() throws SQLException {
        // Initial connection
        DBConnection conn0 = DBConnection.getInstance();

        for (int i = 0; i < 100; i++) {
            assertEquals(conn0, DBConnection.getInstance());
        }
    }

    /**
     * Tests if the default connection gets closed accordingly
     *
     * @throws SQLException
     */
    @Test
    public void testCloseDefaultConnection() throws SQLException {
        assertNotNull(DBConnection.getInstance());
        DBConnection conn = DBConnection.getInstance();
        conn.closeConnection();
        assertNotEquals(conn, DBConnection.getInstance());
    }

    /**
     * Test if multiple connections are distinct from each other
     *
     * @throws SQLException
     */
    @Test
    public void testMultipleConnections() throws SQLException {
        LinkedList<DBConnection> connections = new LinkedList<DBConnection>();

        // Default connections
        connections.push(DBConnection.getInstance());

        // 5 additional connections
        for (int i = 0; i < 5; i++) {
            DBConnection conn = DBConnection.getNewConnection();
            assertFalse(connections.contains(conn));
            connections.push(conn);
        }

        // Close all
        for (DBConnection conn : connections) {
            conn.closeConnection();
        }
    }

}