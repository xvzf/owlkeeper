package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.DBConnection;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DAO to get first entry in testtable
 */
interface testDAO {
    @SqlQuery("select * from testtable where id=1;")
    @RegisterBeanMapper(TestEntry.class)
    TestEntry getEntry();
}

public class SQLFileRunnerTest {

    @Test
    public void testRun() throws IOException {
        // add table and insert entry
        new SQLFileRunner(new String[]{"testadd"}).run();
        //check if table and entry exists
        TestEntry r = DBConnection.getJdbi().withExtension(testDAO.class, dao -> dao.getEntry());
        assertEquals("why", r.getContent());

        //dop it
        new SQLFileRunner(new String[]{"testdrop"}).run();

        // check if the table is dropped by failing to access an entry
        try {
            DBConnection.getJdbi().withExtension(testDAO.class, dao -> dao.getEntry());
        } catch (UnableToExecuteStatementException e) {
            return;
        }
        fail("drop failed");

    }
}
