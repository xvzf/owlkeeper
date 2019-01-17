package de.htwsaar.owlkeeper.storage.db;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * !!! Needs to run in a freshly bootstrapped test database
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBTransactionTest {

    @AfterAll
    public void closeDefaultDatabaseConnection() throws SQLException {
        DBConnection.getInstance().closeConnection();
    }

    @Test
    public void testTransactionNoAutoCommit() throws SQLException {
        new DBTransaction(conn -> assertFalse(conn.getAutoCommit()));
    }

    @Test
    public void testTransactionGetDefaultConnection() throws SQLException {
        new DBTransaction(conn -> {
            assertEquals(DBConnection.getInstance().getSQLConnection(), conn);
        });
    }

    @Test
    public void testTransactionSuccessful() throws SQLException {
        final String username = RandomStringUtils.randomAlphanumeric(32);
        final String email = username + "@test.de";

        // Insert
        new DBTransaction(conn -> {
            PreparedStatement ps = conn.prepareStatement("insert into developer (name, email) values (?,?);");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.execute();
        });

        // Retrieve and validate that everything worked
        Statement st = DBConnection.getInstance().getSQLConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from developer where email = '" + email + "';");
        rs.next();
        assertEquals(username, rs.getString("name"));
        assertEquals(email, rs.getString("email"));
    }

    @Test
    public void testTransactionUnSuccessful() throws SQLException {
        final String username = RandomStringUtils.randomAlphanumeric(32);
        final String email = username + "nonvalidemail.de";

        // Insert
        assertThrows(SQLException.class, () -> {
            new DBTransaction(conn -> {
                PreparedStatement ps = conn.prepareStatement("insert into developer (name, email) values (?,?);");
                ps.setString(1, username);
                ps.setString(2, email);
                ps.execute();
            });
        });

        // Retrieve and validate that nothing was committed to the db
        Statement st = DBConnection.getInstance().getSQLConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from developer where email = '" + email + "';");

        // Shouldn't be stored in DB
        assertFalse(rs.next());
    }

    @Test
    public void testCustomConnectionTransaction() throws SQLException {
        DBConnection conn0 = DBConnection.getInstance();
        DBConnection conn1 = DBConnection.getNewConnection();

        new DBTransaction(conn1, conn -> {
            assertNotEquals(conn0.getSQLConnection(), conn);
            assertEquals(conn1.getSQLConnection(), conn);
        });

        // Custom connection needs to be closed
        conn1.closeConnection();
    }
}