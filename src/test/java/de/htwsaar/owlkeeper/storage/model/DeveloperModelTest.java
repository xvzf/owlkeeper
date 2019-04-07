package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeveloperModelTest {

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    private final String D_NAME_1 = "Robert'); DROP TABLE Developers;--";
    private final String D_EMAIL_1 = "root@example.org";
    private final String D_PW_HASH = "123456";

    private final long D_ID_2 = 3;

    @Test
    void testConstructor() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1, D_EMAIL_1, D_PW_HASH);
        Developer d = dm.getContainer();
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());
        assertEquals(D_PW_HASH, d.getPwhash());
    }

    @Test
    void testSaveLoad() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1, D_EMAIL_1, D_PW_HASH);
        dm.save();
        dm.save();
        long id = dm.getContainer().getId();

        Developer d = dm.getContainer();

        // check against the old values, because .save() will reload the Project from the DB
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());
        assertEquals(D_PW_HASH, d.getPwhash());

        // check against another loaded Project instance with same id
        DeveloperModel dmloaded = new DeveloperModel(id);
        Developer dloaded = dmloaded.getContainer();
        assertEquals(dloaded.getName(), d.getName());
        assertEquals(dloaded.getEmail(), d.getEmail());
        assertEquals(dloaded.getPwhash(), d.getPwhash());
        dmloaded.removeFromDB();
    }

    @Test
    void testGetTeams() {
        DeveloperModel dm = new DeveloperModel(D_ID_2);
        assertEquals(2, dm.getTeams().size());
    }

    @Test
    void testGetTasks() {
        DeveloperModel dm = new DeveloperModel(D_ID_2);
        assertEquals(6, dm.getTasks().size());
    }
}
