package de.htwsaar.owlkeeper.storage.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBTransactionInterface {
    public void exec(Connection conn) throws SQLException;
}
