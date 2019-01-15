package de.htwsaar.owlkeeper.storage.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBTransactionInterface {
    void exec(Connection conn) throws SQLException;
}
