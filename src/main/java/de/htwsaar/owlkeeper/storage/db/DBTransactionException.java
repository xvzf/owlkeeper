package de.htwsaar.owlkeeper.storage.db;

import java.sql.SQLException;

public class DBTransactionException extends SQLException {
    public DBTransactionException() {
        super();
    }

    public DBTransactionException(String s) {
        super(s);
    }
}
