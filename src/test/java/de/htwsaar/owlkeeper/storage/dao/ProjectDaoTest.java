package de.htwsaar.owlkeeper.storage.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Team;

/**
 * Needs to run against test database !!!
 */
class ProjectDaoTest {

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    @Test
    void testGetProjects() {
        List<Project> projects = DBConnection.getJdbi().withExtension(ProjectDao.class, ProjectDao::getProjects);
        List<Long> projectIds = projects.stream().map(Project::getId).collect(Collectors.toList());

        assertEquals(2, projects.size());
        assertTrue(projectIds.contains(1L));
        assertTrue(projectIds.contains(2L));
    }

    @Test
    void testGetStagesForProject() {
        List<ProjectStage> projectStages = DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> {
            return dao.getStagesForProject(1);
        });

        assertEquals(2, projectStages.size());
        projectStages.stream().forEach(stage -> assertEquals(1, stage.getProject()));
    }

    @Test
    void testGetTeams() {
        List<Team> teams = DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> dao.getTeams(2));
        List<Team> allTeams = DBConnection.getJdbi().withExtension(TeamDao.class, TeamDao::getTeams);
        assertEquals(2, teams.size());
        assertTrue(teams.containsAll(allTeams));
    }

    @Test
    void testGetProjectsOfUser() {
        List<Project> expected = new ArrayList<>();
        expected.add(DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> dao.getProject(2)));
        expected.add(DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> dao.getProject(1)));

        System.out.println(expected);

        Developer user = DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.getDeveloper("devel3@owlkeeper.de"));
        List<Project> projects = DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> dao.getProjectsOfUser(user.getId()));

        assertTrue(projects.containsAll(expected));
    }
}