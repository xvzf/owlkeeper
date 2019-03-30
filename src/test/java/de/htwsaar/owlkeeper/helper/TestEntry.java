package de.htwsaar.owlkeeper.helper;

import java.sql.Timestamp;

/**
 * A TestEntry in the database in table testtable
 * Only used by SQLFileRunnerTest
 */
public class TestEntry {
    long id;
    Timestamp created;
    String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "";
    }
}
