package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.ProjectDao;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TeamModelTest {

    private final String T_NAME_1 = "testTeam";
    private final long T_LEADER_1 = 2;

    private final long T_ID_2 = 1;

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    @Test
    void testConstructor() {
        TeamModel pm = new TeamModel(T_NAME_1, T_LEADER_1);
        Team p = pm.getContainer();
        assertEquals(p.getLeader(), T_LEADER_1);
        assertEquals(p.getName(), T_NAME_1);
    }

    @Test
    void testSaveLoadRemove() {
        TeamModel pm = new TeamModel(T_NAME_1, T_LEADER_1);
        pm.save();
        pm.save();
        long id = pm.getContainer().getId();

        Team p = pm.getContainer();

        // check against the old values, because .save() will reload the Team from the DB
        assertEquals(p.getLeader(), T_LEADER_1);
        assertEquals(p.getName(), T_NAME_1);

        // check against another loaded Team instance with same id
        TeamModel pmloaded = new TeamModel(id);
        Team ploaded = pmloaded.getContainer();
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getLeader(), ploaded.getLeader());
        assertEquals(p.getName(), p.getName());
        pmloaded.removeFromDB();
    }

    @Test
    void testGetProjects() {
        TeamModel tm = new TeamModel(T_ID_2);
        List<Project> projects = tm.getProjects(tm.getContainer().getId());
        projects.sort(Comparator.comparingLong(Project::getId));
        List<Project> expected = DBConnection.getJdbi().withExtension(ProjectDao.class, ProjectDao::getProjects);
        expected.sort(Comparator.comparingLong(Project::getId));
        assertEquals(2, projects.size());
        assertEquals(expected,projects);
    }

    @Test
    void testGetTasks() {
        TeamModel tm = new TeamModel(T_ID_2);
        List<Long> tasks = tm.getTasks().stream().map(Task::getId).collect(Collectors.toList());
        List<Long> expected = new ArrayList<>();
        expected.add(2L);
        expected.add(3L);
        expected.add(4L);
        assertEquals(3, tasks.size());
        assertTrue(tasks.containsAll(expected));
        }

    @Test
    void testGetDevelopers() {
        TeamModel tm = new TeamModel(T_ID_2);
        List<Developer> developers = tm.getDevelopers();
        assertEquals(3, developers.size());
    }

    @Test
    void testAddDeveloper() {
        TeamModel tm = new TeamModel(T_ID_2);
        Developer dev = new DeveloperModel(4).getContainer();
        tm.addDeveloper(dev);
        List<Developer> developers = tm.getDevelopers();
        assertEquals(true, developers.contains(dev));
        tm.removeDeveloper(dev);
    }

    @Test
    void testDeleteDeveloper() {
        TeamModel tm = new TeamModel(T_ID_2);
        Developer dev = new DeveloperModel(1).getContainer();
        tm.removeDeveloper(dev);
        List<Developer> developers = tm.getDevelopers();
        assertEquals(false, developers.contains(dev));
        tm.addDeveloper(dev);
    }
}
