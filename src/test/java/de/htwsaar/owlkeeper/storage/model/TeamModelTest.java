package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        assertEquals(1, projects.size());
        assertEquals("Testproject1", projects.get(0).getName());
    }

    @Test
    void testGetTasks() {
        TeamModel tm = new TeamModel(T_ID_2);
        List<Task> tasks = tm.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task 2 description", tasks.get(0).getDescription());
    }
}
