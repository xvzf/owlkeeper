package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.Task;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskModelTest {


    private final String T_NAME_1 = "testTask";
    private final Timestamp T_DEADLINE_1 = new Timestamp(0);
    private final String T_DESCRIPTION_1 = "testDescription";
    private final Timestamp T_FULFILLED_1 = new Timestamp(12);
    private final long T_PROJECT_STAGE_1 = 1;
    private final long T_TEAM_1 = 2;

    @Test
    void testConstructor() {
        TaskModel pm = new TaskModel(T_NAME_1,T_DEADLINE_1,T_DESCRIPTION_1,T_FULFILLED_1,T_PROJECT_STAGE_1,T_TEAM_1);
        Task p = pm.getContainer();
        assertEquals(p.getTeam(), T_TEAM_1);
        assertEquals(p.getFulfilled(), T_FULFILLED_1);
        assertEquals(p.getName(), T_NAME_1);
        assertEquals(p.getDeadline(), T_DEADLINE_1);
        assertEquals(p.getProjectStage(), T_PROJECT_STAGE_1);
        assertEquals(p.getDescription(), T_DESCRIPTION_1);
    }

    @Test
    void testSaveLoadRemove() {
        TaskModel pm = new TaskModel(T_NAME_1,T_DEADLINE_1,T_DESCRIPTION_1,T_FULFILLED_1,T_PROJECT_STAGE_1,T_TEAM_1);
        pm.save();
        long id = pm.getContainer().getId();
        Task p = pm.getContainer();

        // check against the old values, because .save() will reload the Task from the DB
        assertEquals(p.getTeam(), T_TEAM_1);
        assertEquals(p.getProjectStage(), T_PROJECT_STAGE_1);
        assertEquals(p.getDescription(), T_DESCRIPTION_1);

        // check against another loaded Task instance with same id
        TaskModel pmloaded = new TaskModel(id);
        Task ploaded = pmloaded.getContainer();
        assertEquals(p.getTeam(), ploaded.getTeam());
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getProjectStage(), ploaded.getProjectStage());
        assertEquals(p.getDescription(), p.getDescription());
        assertEquals(p.getFulfilled(), p.getFulfilled());
        assertEquals(p.getName(), p.getName());
        assertEquals(p.getDeadline(), p.getDeadline());
        pmloaded.removeFromDB();
    }
}
