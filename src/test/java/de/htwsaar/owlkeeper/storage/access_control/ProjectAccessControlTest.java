package de.htwsaar.owlkeeper.storage.access_control;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.ProjectDao;
import de.htwsaar.owlkeeper.storage.dao.TeamDao;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ProjectAccessControlTest {

    @Test
    void testCreateAndDeleteAllowed() {
        ProjectModel project = new ProjectModel("accessControlProject", "Description of acProject", "waterfall");
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        try {
            project.save();
            project.removeFromDB();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
    }

    @Test
    void testCreateAndDeleteDenied() {
        ProjectModel project = new ProjectModel("accessControlProject", "Description of acProject", "waterfall");
        DeveloperManager.loginDeveloper("devel3@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class, project::save);
        project.getFromDB(1);
        assertThrows(InsufficientPermissionsException.class, project::removeFromDB);
    }

    @Test
    void testEditProject() {
        ProjectModel project = new ProjectModel("accessControlProject", "Description of acProject", "waterfall");
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        project.save();
        // Assign the user's team to the project.
        List<Team> teams = DBConnection.getJdbi()
                .withExtension(TeamDao.class, dao -> dao.getTeamForDeveloper(DeveloperManager.getCurrentId()));
        System.out.println(teams);
        long teamId = DBConnection.getJdbi()
                .withExtension(TeamDao.class, dao -> dao.getTeamForDeveloper(DeveloperManager.getCurrentId())).get(0)
                .getId();
        int relationId = DBConnection.getJdbi().withExtension(ProjectDao.class,
                dao -> dao.assignTeamToProject(project.getContainer().getId(), teamId));

        try {
            project.getContainer().setDescription("Some other description");
            project.save();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
        // Test another developer that shouldn't be allowed
        DeveloperManager.loginDeveloper("devel4@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class, () -> {
            project.getContainer().setDescription("description");
            project.save();
        });

        // Clean up
        DeveloperManager.loginDeveloper("devel2@owlkeeper.de");
        project.removeFromDB();
        DBConnection.getJdbi()
                .useHandle(handle -> handle.execute("delete from team_project_relation where id = ?;", relationId));

    }

    @Test
    void testGetStages() {
        ProjectModel project = new ProjectModel(2);
        DeveloperManager.loginDeveloper("devel4@owlkeeper.de");
        try {
            project.getStages();
        } catch (InsufficientPermissionsException e) {
            fail(e);
        }
        DeveloperManager.loginDeveloper("devel5@owlkeeper.de");
        assertThrows(InsufficientPermissionsException.class, project::getStages);
    }
}
