package de.htwsaar.owlkeeper.storage.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * DBConnectionTest
 *
 * !!! Docker based PostgreSQL needs to be up and running !!!
 *
 */
public class DBConnectionTest {

    @AfterEach
    public void cleanUp() {
        DBConnection.closeConnection();
    }

    /**
     * Test if the framework setups a connection to the running database
     *
     * @throws SQLException
     */
    @Test
    public void testGetConnection() throws SQLException {
        assertNotNull(DBConnection.getConnection());
    }

    /**
     * Tests if the default connection is setup only once
     *
     * @throws SQLException
     */
    @Test
    public void testMultipleDefaultConnectionsEqual() throws SQLException {
        // Initial connection
        Connection conn0 = DBConnection.getConnection();

        for (int i = 0; i < 100; i++) {
            assertEquals(conn0, DBConnection.getConnection());
        }
    }

    /**
     * Tests if the default connection gets closed accordingly
     *
     * @throws SQLException
     */
    @Test
    public void testCloseDefaultConnection() throws SQLException {
        assertNotNull(DBConnection.getConnection());
        DBConnection.closeConnection();
        assertNull(DBConnection.currentConnection);
    }

    /**
     * Test if multiple connections are distinct from each other
     *
     * @throws SQLException
     */
    @Test
    public void testMultipleConnections() throws SQLException {
        LinkedList<Connection> connections = new LinkedList<Connection>();

        // Default connections
        connections.push(DBConnection.getConnection());

        // 5 additional connections
        for (int i = 0; i < 5; i++) {
            Connection conn = DBConnection.getNewConnection();
            assertFalse(connections.contains(conn));
            connections.push(conn);
        }

        // Close all
        for (Connection conn : connections) {
            conn.close();
        }
    }

}