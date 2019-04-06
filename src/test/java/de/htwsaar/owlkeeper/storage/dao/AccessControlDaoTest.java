package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessControlDaoTest {

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    @Test
    void testAllowedToComment() {
        final Boolean allowed = DBConnection.getJdbi().withExtension(AccessControlDao.class, dao -> {
            return dao.isAssignedToTask(1, 4);
        });
        assertTrue(allowed);
    }

    @Test
    void testNotAllowedToComment() {
        final Boolean allowed = DBConnection.getJdbi().withExtension(AccessControlDao.class, dao -> {
            return dao.isAssignedToTask(5, 1);
        });
        assertFalse(allowed);
    }

}