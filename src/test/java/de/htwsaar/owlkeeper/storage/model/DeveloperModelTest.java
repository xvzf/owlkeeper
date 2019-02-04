package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeveloperModelTest {

    private final String D_NAME_1 = "Robert'); DROP TABLE Developers;--";
    private final String D_ROLE_1 = "child";
    private final String D_EMAIL_1 = "root@example.org";
    private final String D_PWHASH_1 = "*insert my code here*";
    private final boolean D_CHIEF_1 = false;

    @Test
    void testConstructor() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1, D_ROLE_1, D_EMAIL_1, D_PWHASH_1, D_CHIEF_1);
        Developer d = dm.getContainer();
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());
        assertEquals(D_ROLE_1, d.getRole());
        assertEquals(D_PWHASH_1, d.getPwhash());
        assertEquals(D_CHIEF_1, d.isChief());
    }

    @Test
    void testSaveLoad() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1, D_ROLE_1, D_EMAIL_1, D_PWHASH_1, D_CHIEF_1);
        dm.save();
        long id = dm.getContainer().getId();

        Developer d = dm.getContainer();

        // check against the old values, because .save() will reload the Project from the DB
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());
        assertEquals(D_ROLE_1, d.getRole());
        assertEquals(D_PWHASH_1, d.getPwhash());
        assertEquals(D_CHIEF_1, d.isChief());

        // check against another loaded Project instance with same id
        DeveloperModel dmloaded = new DeveloperModel(id);
        Developer dloaded = dmloaded.getContainer();
        assertEquals(dloaded.getName(), d.getName());
        assertEquals(dloaded.getEmail(), d.getEmail());
        assertEquals(dloaded.getRole(), d.getRole());
        assertEquals(dloaded.getPwhash(), d.getPwhash());
        assertEquals(dloaded.isChief(), d.isChief());
        dmloaded.removeFromDB();
    }
}