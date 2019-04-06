package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.*;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import static de.htwsaar.owlkeeper.service.PermissionHandler.checkPermission;

public class TaskModel extends AbstractModel<Task, TaskDao> {

    private static Logger logger = LogManager.getLogger(TaskModel.class);
    private static Function<Long, ExtensionCallback<Task, TaskDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getTask(id));
    private static Function<Long, ExtensionCallback<Integer, TaskDao, InsufficientPermissionsException>> removeCallbackFactory = id -> (dao -> {
        checkPermission(Permissions.DELETE_TASK.get());
        return dao.deleteTask(id);
    });
    private static Function<Task, ExtensionCallback<Integer, TaskDao, InsufficientPermissionsException>> saveCallbackFactory1 = p -> (dao -> (p
            .getId() != 0 ? dao.updateTask(p) : dao.insertTask(p)));


    /**
     * Constructor for new Task and TaskModel. Generates new Task and saves it into the container
     * For parameters check Task class
     */
    public TaskModel(String name, Timestamp deadline, String description, Timestamp fullfilled, long projectstage, long team) {
        super(logger, TaskDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        super.setContainer(new Task());

        // To create a Task, the creating user should already be in a team assigned to the project.
        // This can be accomplished by manually adding the team to the project_team_relation
        checkPermission(user -> DBConnection.getJdbi().withExtension(ProjectDao.class, projectDao -> projectDao.getProjectsOfUser(user.getId())
                .contains(DBConnection.getJdbi()
                        .withExtension(ProjectStageDao.class, stageDao -> stageDao.getProject(projectstage)))));

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
        // To create a Task, the creating user should already be in a team assigned to the project.
        // This can be accomplished by manually adding the team to the project_team_relation
        checkPermission(user -> DBConnection.getJdbi().withExtension(ProjectDao.class, projectDao -> projectDao.getProjectsOfUser(user.getId())
                .contains(DBConnection.getJdbi().withExtension(ProjectStageDao.class, stageDao -> stageDao.getProject(getContainer().getProjectStage())))));
    }

    /**
     * returns TaskIds the original Task is depeding on.
     *
     * @return dependingTask
     */

    public List<Integer> getDependencies () {
        long taskId = this.getContainer().getId();
        return DBConnection.getJdbi().withExtension(TaskDao.class, (dao -> dao.getDependencies(taskId)));
    }

    /**
     * set new Task the original Task depends on
     */
    public void setDependency(Task dependsTask) {
        checkPermission(user -> DBConnection.getJdbi().withExtension(AccessControlDao.class,
                dao -> dao.isAssignedToTask(user.getId(), getContainer().getId())));

        long taskId = this.getContainer().getId();
        long dependsId = dependsTask.getId();

        DBConnection.getJdbi().withExtension(TaskDao.class, (dao -> dao.setDependency(taskId, dependsId)));
    }

    /**
     * retrieves all comments for a task
     */
    public List<TaskComment> getComments() {
        long id = this.getContainer().getId();
        List<TaskComment> TCList = DBConnection.getJdbi().withExtension(TaskCommentDao.class, (dao -> dao.getCommentsForTask(id)));
        return TCList;
    }

    public void setFulfilled(Timestamp fulfilled) {
        checkPermission(user -> DBConnection.getJdbi().withExtension(AccessControlDao.class,
                dao -> dao.isAssignedToTask(user.getId(), getContainer().getId())));
        getContainer().setFulfilled(fulfilled);
    }

    public void setTeam(long team) {
        checkPermission(Permissions.ASSIGN_TEAM_TO_TASK.get());
        getContainer().setTeam(team);
    }
}
