package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProjectStageModelTest {

    private final String PS_NAME_1 = "testProjectStage";
    private final long PS_INDEX_1 = 3;
    private final long PS_PROJECT_1 = 2;

    private final long PS_ID_2 = 1;
    private final long PS_ACTUAL_NO_OF_TASKS = 2;

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    @Test
    void testConstructor() {
        ProjectStageModel pm = new ProjectStageModel(PS_NAME_1, PS_PROJECT_1, PS_INDEX_1);
        ProjectStage p = pm.getContainer();
        assertEquals(p.getProject(), PS_PROJECT_1);
        assertEquals(p.getIndex(), PS_INDEX_1);
        assertEquals(p.getName(), PS_NAME_1);
    }

    @Test
    void testSaveLoadRemove() {
        ProjectStageModel pm = new ProjectStageModel(PS_NAME_1, PS_PROJECT_1, PS_INDEX_1);
        pm.save();
        pm.save();
        long id = pm.getContainer().getId();

        ProjectStage p = pm.getContainer();

        // check against the old values, because .save() will reload the ProjectStage from the DB
        assertEquals(p.getProject(), PS_PROJECT_1);
        assertEquals(p.getIndex(), PS_INDEX_1);
        assertEquals(p.getName(), PS_NAME_1);

        // check against another loaded ProjectStage instance with same id
        ProjectStageModel pmloaded = new ProjectStageModel(id);
        ProjectStage ploaded = pmloaded.getContainer();
        assertEquals(p.getProject(), ploaded.getProject());
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getIndex(), ploaded.getIndex());
        assertEquals(p.getName(), p.getName());
        pmloaded.removeFromDB();
    }

    @Test
    void testGetTasks() {
        ProjectStageModel pm = new ProjectStageModel(PS_ID_2);
        List<Task> tasks = pm.getTasks();
        assertEquals(tasks.size(), PS_ACTUAL_NO_OF_TASKS);
    }

    @Test
    void testGetTeams() {
        ProjectStageModel pm = new ProjectStageModel(PS_ID_2);
        List<Team> teams = pm.getTeams();
        assertEquals(1, teams.size());
        assertEquals("Team 1", teams.get(0).getName());
    }
}
