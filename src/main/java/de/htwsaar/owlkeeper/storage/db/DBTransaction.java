package de.htwsaar.owlkeeper.storage.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database transaction helper
 */
public class DBTransaction {
    private static final Logger logger = LogManager.getLogger(DBTransaction.class);

    private final Connection conn;
    private final DBTransactionInterface exec;


    /**
     * Database transaction helper, currently singlethreaded !!!
     *
     * @param exec Pass the function that should be executed
     * @throws SQLException Generic SQL Error happened (e.g. while connecting to the DB
     * @throws DBTransactionException Error happened while committing the transaction
     */
    public DBTransaction(DBTransactionInterface exec) throws SQLException, DBTransactionException {
        this(DBConnection.getInstance(), exec);
    }
    /**
     * Database transaction helper
     *
     * @param conn Database Connection
     * @param exec Pass the function that should be executed
     * @throws SQLException Generic SQL Error happened (e.g. while connecting to the DB
     * @throws DBTransactionException Error happened while committing the transaction
     */
    public DBTransaction(DBConnection conn, DBTransactionInterface exec) throws SQLException, DBTransactionException {
        this.conn = conn.getSQLConnection();
        this.exec = exec;
        this.run();
    }

    /**
     * Actual runner
     *
     * @throws SQLException @constructor
     * @throws DBTransactionException @constructor
     */
    private void run() throws SQLException, DBTransactionException {
        try {
            logger.debug("Setting up transaction");
            conn.setAutoCommit(false); // Disable auto commit
            exec.exec(conn); // Execute whatever was requested
            conn.commit();
            logger.debug("Transaction committed");
        } catch (SQLException se) {
            logger.error("Could not commit transaction, rollback initiated", se);
            conn.rollback();
            throw se;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
