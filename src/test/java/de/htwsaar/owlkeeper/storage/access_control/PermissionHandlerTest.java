package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.AccessControlDao;
import org.junit.jupiter.api.Test;

import static de.htwsaar.owlkeeper.service.PermissionHandler.checkPermission;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class PermissionHandlerTest {
    @Test
    void testStaticPermission() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        try {
            checkPermission(Permissions.CREATE_PROJECT.get());
            checkPermission(Permissions.DISSOLVE_TEAM.get());
            checkPermission(Permissions.DELETE_PROJECT_STAGE.get());
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
        assertThrows(InsufficientPermissionsException.class, () -> checkPermission(Permissions.CREATE_TASK.get()));
    }

    @Test
    void testDynamicPermission() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class, () -> {
            checkPermission(user -> DBConnection.getJdbi()
                    .withExtension(AccessControlDao.class, dao -> dao.isAssignedToTask(user.getId(), 1)));
        });
    }
}
