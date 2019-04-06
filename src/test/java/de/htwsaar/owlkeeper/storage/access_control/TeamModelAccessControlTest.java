package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import de.htwsaar.owlkeeper.storage.model.TeamModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TeamModelAccessControlTest {
    @Test
    void testCreateAndDeleteAllowed() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        TeamModel team = new TeamModel("TeamAC", 3);
        try {
            team.save();
            team.removeFromDB();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testCreateDenied() {
        DeveloperManager.loginDeveloper("devel3@owlkeeper.de");
        TeamModel team = new TeamModel("TeamAC", 3);
        assertThrows(InsufficientPermissionsException.class, team::save);
    }

    @Test
    void testDeleteDenied() {
        DeveloperManager.loginDeveloper("devel3@owlkeeper.de");
        TeamModel team = new TeamModel(1);
        assertThrows(InsufficientPermissionsException.class, team::removeFromDB);
    }

    @Test
    void testGetTasksAllowed() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        try {
            TeamModel team = new TeamModel(1);
            team.getTasks();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testGetTasksDenied() {
        DeveloperManager.loginDeveloper("devel4@owlkeeper.de");
        TeamModel team = new TeamModel(1);
        assertThrows(InsufficientPermissionsException.class, team::getTasks);
    }

    @Test
    void testAddAndRemoveDeveloperAllowed() {
        DeveloperManager.loginDeveloper("devel4@owlkeeper.de");
        TeamModel team = new TeamModel(2);
        DeveloperModel dev = new DeveloperModel(2);
        try {
            team.addDeveloper(dev.getContainer());
            team.removeDeveloper(dev.getContainer());
            team.addDeveloper(dev.getContainer());

            DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
            team.removeDeveloper(dev.getContainer());
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testAddDeveloperDenied() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        TeamModel team = new TeamModel(1);
        DeveloperModel dev = new DeveloperModel(3);
        assertThrows(InsufficientPermissionsException.class, () -> team.addDeveloper(dev.getContainer()));
    }

    @Test
    void testRemoveDeveloperDenied() {
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        TeamModel team = new TeamModel(1);
        DeveloperModel dev = new DeveloperModel(3);
        assertThrows(InsufficientPermissionsException.class, () -> team.removeDeveloper(dev.getContainer()));
    }
}
