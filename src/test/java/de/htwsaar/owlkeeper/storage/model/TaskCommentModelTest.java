package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskCommentModelTest {

    private final String TC_CONTENT_1 = "testTaskComment";
    private final long TC_DEVELOPER_1 = 1;
    private final long TC_TASK_1 = 2;

    @BeforeAll
    static void setUp() {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
    }

    @Test
    void testConstructor() {
        TaskCommentModel pm = new TaskCommentModel(TC_CONTENT_1, TC_DEVELOPER_1, TC_TASK_1);
        TaskComment p = pm.getContainer();
        assertEquals(p.getTask(), TC_TASK_1);
        assertEquals(p.getDeveloper(), TC_DEVELOPER_1);
        assertEquals(p.getContent(), TC_CONTENT_1);
    }

    @Test
    void testSaveLoadRemove() {
        TaskCommentModel pm = new TaskCommentModel(TC_CONTENT_1, TC_DEVELOPER_1, TC_TASK_1);
        pm.save();
        pm.save();
        long id = pm.getContainer().getId();

        TaskComment p = pm.getContainer();

        // check against the old values, because .save() will reload the TaskComment from the DB
        assertEquals(p.getTask(), TC_TASK_1);
        assertEquals(p.getDeveloper(), TC_DEVELOPER_1);
        assertEquals(p.getContent(), TC_CONTENT_1);

        // check against another loaded TaskComment instance with same id
        TaskCommentModel pmloaded = new TaskCommentModel(id);
        TaskComment ploaded = pmloaded.getContainer();
        assertEquals(p.getTask(), ploaded.getTask());
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getDeveloper(), ploaded.getDeveloper());
        assertEquals(p.getContent(), p.getContent());
        pmloaded.removeFromDB();
    }
}
