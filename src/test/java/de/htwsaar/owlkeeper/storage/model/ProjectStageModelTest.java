package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProjectStageModelTest {

    private final String PS_NAME_1 = "testProjectStage";
    private final long PS_INDEX_1 = 1;
    private final long PS_PROJECT_1 = 2;

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
}
