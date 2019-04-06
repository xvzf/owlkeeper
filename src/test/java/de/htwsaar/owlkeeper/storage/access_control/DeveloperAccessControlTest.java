package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeveloperAccessControlTest {

    @Test
    void testSaveCallbackAllowed() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        DeveloperModel model1 = new DeveloperModel(DeveloperManager.getDeveloper());
        DeveloperModel model2 = new DeveloperModel("devel2@owlkeeper.de");
        // Check loading works
        assertEquals(DeveloperManager.getDeveloper(), model1.getContainer());
        assertEquals(DeveloperManager.getDeveloper(), model2.getContainer());

        try {
            DeveloperManager.getCurrentDeveloper().save();
            model1.save();
            model2.save();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testSaveCallbackDenied() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        DeveloperModel model1 = new DeveloperModel("devel1@owlkeeper.de");
        DeveloperModel model2 = new DeveloperModel("devel3@owlkeeper.de");
        // Check loading works
        assertNotEquals(DeveloperManager.getDeveloper(), model1.getContainer());
        assertNotEquals(DeveloperManager.getDeveloper(), model2.getContainer());

        assertThrows(InsufficientPermissionsException.class, model1::save);
        assertThrows(InsufficientPermissionsException.class, model2::save);
    }

    @Test
    void testDeleteCallbackDenied() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        DeveloperModel devel2 = new DeveloperModel("devel1@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class, devel2::removeFromDB);
    }
}
