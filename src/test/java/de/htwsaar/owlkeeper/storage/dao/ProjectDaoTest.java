package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Needs to run against test database !!!
 */
class ProjectDaoTest {

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
}