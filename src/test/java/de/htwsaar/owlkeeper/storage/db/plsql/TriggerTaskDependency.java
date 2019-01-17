package de.htwsaar.owlkeeper.storage.db.plsql;

import de.htwsaar.owlkeeper.storage.db.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * !!! Needs to run in a freshly bootstrapped test database
 * <p>
 * Dummy data contains a task constraint {task: 1, depends: 3}
 */
class TriggerTaskDependency {

    /**
     * Runs a db query
     *
     * @param query
     * @throws SQLException
     */
    void runQuery(final String query) throws SQLException {
        final Connection conn = DBConnection.getInstance().getSQLConnection();
        Statement st = conn.createStatement();
        st.execute(query);
    }

    @Test
    void testInsertNewDependency() throws SQLException {
        runQuery("insert into task_dependency (task, depends) values (1,2);");
    }

    @Test
    void testDoubleEntriesNotAllowed() {
        assertThrows(SQLException.class, () -> runQuery("insert into task_dependency (task, depends) values (1,3);"));
    }

    @Test
    void testCircularDependenciesNotAllowed() {
        assertThrows(SQLException.class, () -> runQuery("insert into task_dependency (task, depends) values (3,1);"));
    }

}
