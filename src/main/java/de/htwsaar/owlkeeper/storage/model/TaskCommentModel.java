package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.dao.TaskCommentDao;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.function.Function;

public class TaskCommentModel extends AbstractModel<TaskComment, TaskCommentDao> {

    /**
     * long id;
     *     Timestamp created;
     *     String content;
     *     long developer;
     *     long task;
     */

    private static Logger logger = LogManager.getLogger(TaskCommentModel.class);
    private static Function<Long, ExtensionCallback<TaskComment, TaskCommentDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getComment(id));
    private static Function<Long, ExtensionCallback<Integer, TaskCommentDao, InsufficientPermissionsException>> deleteCallbackFactory = id -> (dao -> dao.deleteTaskComment(id));
    private static Function<TaskComment, ExtensionCallback<Integer, TaskCommentDao, InsufficientPermissionsException>> saveCallbackFactory1 =
            p -> (dao -> (p.getId() != 0 ? dao.updateTaskComment(p) : dao.insertTaskComment(p)));


    /**
     * Constructor for new TaskComment and TaskCommentModel. Generates the TaskComment and saves it into the container
     * For parameters check TaskComment class
     */
    public TaskCommentModel(String content, long developer, long task) {

        super(logger, TaskCommentDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        super.setContainer(new TaskComment());
        this.getContainer().setContent(content);
        this.getContainer().setDeveloper(developer);
        this.getContainer().setTask(task);
    }

    /**
     * Queries a TaskComment out of the db
     *
     * @param id Id in the db
     */
    public TaskCommentModel(long id) {
        super(logger, TaskCommentDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing TaskComments
     *
     * @param TaskComment TaskComment
     */
    public TaskCommentModel(TaskComment TaskComment) {
        super(TaskComment, logger, TaskCommentDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
    }
}
