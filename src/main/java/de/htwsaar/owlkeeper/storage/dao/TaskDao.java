package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface TaskDao {

    /**
     * Query task by id
     *
     * @param id
     * @return
     */
    @SqlQuery("select * from task where id = ?;")
    @RegisterBeanMapper(Task.class)
    Task getTask(long id);

    /**
     * Insert task into db
     *
     * @param t
     * @return
     */
    @SqlQuery("insert into task"
            + "(deadline, name, description, fulfilled, project_stage, team) values  "
            + "(:deadline, :name, :description, :fulfilled, :projectStage, :team) "
            + "returning id;"
    )
    int insertTask(@BindBean Task t);

    /**
     * Update task
     *
     * @param t
     * @return
     */
    @SqlQuery("update task set"
            + " deadline = :deadline"
            + ", name = :name"
            + ", description = :description"
            + ", fulfilled = :fulfilled"
            + ", project_stage = :projectStage"
            + ", team = :team "
            + "where id = :id returning id;"
    )
    int updateTask(@BindBean Task t);


    /**
     * Deletes a task
     *
     * @param id
     * @return
     */
    @SqlQuery("delete from task where id = ? returning id;")
    int deleteTask(long id);

    /**
     * Retrieves all tasks for a sepecific project stage
     *
     * @param id project stage id
     * @return
     */
    @SqlQuery("select * from task where project_stage = ? order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getTasksForProjectStage(long id);

    /**
     * Retrieves fulfilled tasks for a specific project stage
     *
     * @param id project stage id
     * @return
     */
    @SqlQuery("select * from task where project_stage = ? and fulfilled is not null order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getFulfilledTasksforProjectStage(long id);

    /**
     * Retrieves pending tasks for a specific project stage
     *
     * @param id project stage id
     * @return
     */
    @SqlQuery("select * from task where project_stage = ? and fulfilled is null order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksForProjectStage(long id);

    /**
     * Retrieves pending tasks in a list of tasks which block the current task directly(!)
     *
     * @param id task id
     * @return
     */
    @SqlQuery(
            "select d.id, d.created, d.deadline, d.name, d.description, d.fulfilled, d.project_stage, d.team "
                    + "from task_dependency as td "
                    + "left join task as t on td.task = t.id "
                    + "left join task as d on td.depends = d.id "
                    + "where d.fulfilled is null and t.id = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksBeforeUnblocked(long id);

    /**
     * Retrieves tasks for a specific developer and project stage
     *
     * @param projectStageId
     * @param developerId
     * @return
     */
    @SqlQuery(
            "select t.id, t.created, t.deadline, t.name, t.description, t.fulfilled, t.project_stage, t.team "
                    + "from task as t "
                    + "left join team as tm on t.team = tm.id "
                    + "left join developer_team_relation as r on r.team = tm.id "
                    + "left join developer as d on r.developer = d.id "
                    + "where t.project_stage = ? and d.id = ?; "
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getTasksForDeveloperAndProjectStage(long projectStageId, long developerId);

    /**
     * Retrieves fulfilled tasks for a specific developer and project stage
     *
     * @param projectStageId
     * @param developerId
     * @return
     */
    @SqlQuery(
            "select t.id, t.created, t.deadline, t.name, t.description, t.fulfilled, t.project_stage, t.team "
                    + "from task as t "
                    + "left join team as tm on t.team = tm.id "
                    + "left join developer_team_relation as r on r.team = tm.id "
                    + "left join developer as d on r.developer = d.id "
                    + "where t.fulfilled is not null and t.project_stage = ? and d.id = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getFulfilledTasksForDeveloperAndProjectStage(long projectStageId, long developerId);

    /**
     * Retrieves pending tasks for a specific developer and project stage
     *
     * @param projectStageId
     * @param developerId
     * @return
     */
    @SqlQuery("select t.id, t.created, t.deadline, t.name, t.description, t.fulfilled, t.project_stage, t.team "
            + "from task as t "
            + "left join team as tm on t.team = tm.id "
            + "left join developer_team_relation as r on r.team = tm.id "
            + "left join developer as d on r.developer = d.id "
            + "where t.fulfilled is null and t.project_stage = ? and d.id = ?;")
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksForDeveloperAndProjectStage(long projectStageId, long developerId);

    /**
     * Retrieves the TaskId the Task depends on
     *
     * @param taskId
     * @return
     */
    @SqlQuery("select depends from task_dependency where task = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Integer> getDependencies (long taskId);

    /**
     * inserts a task which the original task depends on
     *
     * @param taskId
     * @param dependsId
     * @return dependsId
     */
    @SqlQuery("insert into task_dependency (task, depends) values(?, ?) returning depends;"
    )
    @RegisterBeanMapper(Task.class)
    int setDependency (long taskId, long dependsId);
}
