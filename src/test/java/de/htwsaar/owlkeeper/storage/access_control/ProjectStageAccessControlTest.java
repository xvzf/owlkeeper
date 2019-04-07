package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ProjectStageAccessControlTest {

    @Test
    void testCreateAndDeleteStage() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        ProjectStageModel stage = new ProjectStageModel("Stage 3", 1, 2);
        try {
            stage.save();
            stage = new ProjectStageModel(stage.getContainer());
            stage.removeFromDB();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testCreateStageDenied() {
        DeveloperManager
                .loginDeveloper("devel3@owlkeeper.de"); // User that is assigned but doesn't have creation rights.
        ProjectStageModel stage = new ProjectStageModel("Stage 3", 1, 2);
        assertThrows(InsufficientPermissionsException.class, stage::save);
    }

    @Test
    void testCreateStageDenied2() {
        ProjectStageModel stage = new ProjectStageModel("Stage 3", 2, 2);
        DeveloperManager.loginDeveloper("devel5@owlkeeper.de"); // User has creation rights but isn't assigned.
        assertThrows(InsufficientPermissionsException.class, stage::save);
    }

    @Test
    void testDeleteStageDenied() {
        DeveloperManager
                .loginDeveloper("devel3@owlkeeper.de"); // User that is assigned but doesn't have creation rights.
        ProjectStageModel stage = new ProjectStageModel(1);
        assertThrows(InsufficientPermissionsException.class, stage::removeFromDB);
    }

    @Test
    void testDeleteStageDenied2() {
        ProjectStageModel stage = new ProjectStageModel(3);
        DeveloperManager.loginDeveloper("devel5@owlkeeper.de"); // User has creation rights but isn't assigned.
        assertThrows(InsufficientPermissionsException.class, stage::removeFromDB);
    }
}
