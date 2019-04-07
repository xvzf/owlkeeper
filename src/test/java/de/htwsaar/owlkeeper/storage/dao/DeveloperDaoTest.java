package de.htwsaar.owlkeeper.storage.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Developer;

/**
 * Needs to run against test database !!!
 */
class DeveloperDaoTest {

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }


    @Test
    void testGetDevelopers() {
        List<Developer> developers = DBConnection.getJdbi().withExtension(DeveloperDao.class, DeveloperDao::getDevelopers);

        assertEquals(5, developers.size());
    }

    @Test
    void testGetDeveloper() {
        Developer developer = DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> {
            return dao.getDeveloper(1);
        });

        assertEquals(1, developer.getId());
        assertEquals("Developer 1", developer.getName());
        assertEquals("devel1@owlkeeper.de", developer.getEmail());
    }

    @Test
    void testEditGroup() {
        List<String> initialGroups = DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.getGroup(3));
        DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.addToGroup(3,"admin"));
        assertTrue(DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.getGroup(3)).contains("admin"));
        DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.removeFromGroup(3,"admin"));
        assertEquals(initialGroups, DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.getGroup(3)));
    }

}