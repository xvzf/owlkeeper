package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.DBConnection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessControlDaoTest {

    @Test
    void testAllowedToComment() {
        final Boolean allowed = DBConnection.getJdbi().withExtension(AccessControlDao.class, dao -> {
            return dao.allowedToCommentTask(1, 2);
        });
        assertTrue(allowed);
    }

    @Test
    void testNotAllowedToComment() {
        final Boolean allowed = DBConnection.getJdbi().withExtension(AccessControlDao.class, dao -> {
            return dao.allowedToCommentTask(4, 1);
        });
        assertFalse(allowed);
    }

}