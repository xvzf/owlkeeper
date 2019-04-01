package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Needs to run against test database !!!
 */
class ProjectDaoTest {

    @BeforeEach
    void setUp() {
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
        assertEquals(1, teams.size());
        assertEquals("Team 2", teams.get(0).getName());
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