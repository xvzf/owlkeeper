package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeveloperModelTest {

    private final String D_NAME_1 = "Robert'); DROP TABLE Developers;--";
    private final String D_EMAIL_1 = "root@example.org";

    @Test
    void testConstructor() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1, D_EMAIL_1);
        Developer d = dm.getContainer();
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());
    }

    @Test
    void testSaveLoad() {
        DeveloperModel dm = new DeveloperModel(D_NAME_1,D_EMAIL_1);
        dm.save();
        long id = dm.getContainer().getId();

        Developer d = dm.getContainer();

        // check against the old values, because .save() will reload the Project from the DB
        assertEquals(D_NAME_1, d.getName());
        assertEquals(D_EMAIL_1, d.getEmail());

        // check against another loaded Project instance with same id
        DeveloperModel dmloaded = new DeveloperModel(id);
        Developer dloaded = dmloaded.getContainer();
        assertEquals(dloaded.getName(), d.getName());
        assertEquals(dloaded.getEmail(), d.getEmail());
        assertEquals(dloaded.getPwhash(), d.getPwhash());
        dmloaded.removeFromDB();
    }
}
