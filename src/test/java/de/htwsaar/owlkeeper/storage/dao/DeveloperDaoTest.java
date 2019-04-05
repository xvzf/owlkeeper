package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

}