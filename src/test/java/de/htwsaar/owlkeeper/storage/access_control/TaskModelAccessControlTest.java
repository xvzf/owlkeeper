package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.model.TaskModel;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskModelAccessControlTest {

    @Test
    void testCreateTaskAllowed() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        try {
            new TaskModel("ACTask", new Timestamp(0), "Some description", null, 1, 0);
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testCreateTaskDenied() {
        DeveloperManager.loginDeveloper("devel5@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class,
                () -> new TaskModel("ACTask", new Timestamp(0), "Some description", null, 3, 0));
    }

}
