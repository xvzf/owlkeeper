package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProjectModelTest {

    private final String P_NAME_1 = "testproject";
    private final String P_DESCRIPTION_1 = "lorem ipsum";
    private final String P_TYPE_1 = "spiral";

    @Test
    void testConstructor() {
        ProjectModel pm = new ProjectModel(P_NAME_1, P_DESCRIPTION_1, P_TYPE_1);
        Project p = pm.getContainer();
        assertEquals(p.getType(), P_TYPE_1);
        assertEquals(p.getDescription(), P_DESCRIPTION_1);
        assertEquals(p.getName(), P_NAME_1);
    }

    @Test
    void testSaveLoadRemove() {
        ProjectModel pm = new ProjectModel(P_NAME_1, P_DESCRIPTION_1, P_TYPE_1);
        pm.save();
        long id = pm.getContainer().getId();

        Project p = pm.getContainer();

        // check against the old values, because .save() will reload the Project from the DB
        assertEquals(p.getType(), P_TYPE_1);
        assertEquals(p.getDescription(), P_DESCRIPTION_1);
        assertEquals(p.getName(), P_NAME_1);

        // check against another loaded Project instance with same id
        ProjectModel pmloaded = new ProjectModel(id);
        Project ploaded = pmloaded.getContainer();
        assertEquals(p.getType(), ploaded.getType());
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getDescription(), ploaded.getDescription());
        assertEquals(p.getName(), p.getName());
        pmloaded.removeFromDB();
    }
}
