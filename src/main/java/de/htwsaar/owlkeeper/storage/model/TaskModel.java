package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.TaskDao;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.sql.Timestamp;
import java.util.function.Function;

public class TaskModel extends AbstractModel<Task, TaskDao> {

    private static Logger logger = LogManager.getLogger(TaskModel.class);
    private static Function<Long, ExtensionCallback<Task, TaskDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getTask(id));
    private static Function<Long, ExtensionCallback<Integer, TaskDao, RuntimeException>> removeCallbackFactory = id -> (dao -> dao.deleteTask(id));
    private static Function<Task, ExtensionCallback<Integer, TaskDao, RuntimeException>> saveCallbackFactory1 =
            p -> (dao -> (p.getId() != 0 ? dao.updateTask(p) : dao.insertTask(p)));


    /**
     * Constructor for new Task and TaskModel. Generates new Task and saves it into the container
     * For parameters check Task class
     */
    public TaskModel(String name, Timestamp deadline, String description, Timestamp fullfilled, long projectstage, long team) {

        super(logger, TaskDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        super.setContainer(new Task());
        this.getContainer().setName(name);
        this.getContainer().setDeadline(deadline);
        this.getContainer().setDescription(description);
        this.getContainer().setFulfilled(fullfilled);
        this.getContainer().setProjectStage(projectstage);
        this.getContainer().setTeam(team);
    }

    /**
     * Queries a Task out of the db
     *
     * @param id Id in the db
     */
    public TaskModel(long id) {
        super(logger, TaskDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing Tasks
     *
     * @param Task Task
     */
    public TaskModel(Task Task) {
        super(Task, logger, TaskDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
    }

    public void addComment(TaskComment com) {
        //DBConnection.getJdbi().withExtension(TaskComment.class, (dao -> dao.(id)));

    }
}
