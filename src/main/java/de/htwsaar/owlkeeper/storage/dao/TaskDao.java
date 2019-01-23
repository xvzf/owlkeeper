package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface TaskDao {

    @SqlQuery("select * from task where project_stage = ? order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getTasksForProjectStage(long id);

    @SqlQuery("select * from task where project_stage = ? and fullfilled is not null order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getFullfilledTasksforProjectStage(long id);

    @SqlQuery("select * from task where project_stage = ? and fullfilled is null order by created asc;")
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksForProjectStage(long id);

    @SqlQuery(
            "select d.id, d.created, d.deadline, d.name, d.description, d.fullfilled, d.project_stage, d.team "
                    + "from task_dependency as td "
                    + "left join task as t on td.task = t.id "
                    + "left join task as d on td.depends = d.id "
                    + "where d.fullfilled is null and t.id = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksBeforeUnblocked(long id);

    @SqlQuery(
            "select t.it. t.createt. t.t.at.ine, t.name, t.t.scription, t.fullfillet. t.project_stage, t.team "
                    + "from task as t"
                    + "left join team as tm on t.team = tm.id "
                    + "left join developer_team_relation as r on r.team = tm.id"
                    + "left join developer as d on r.developer = d.id"
                    + "where t.project_stage = ? and d.id = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getTasksForDeveloperAndProjectStage(long projectStageId, long developerId);

    @SqlQuery(
            "select t.it. t.createt. t.t.at.ine, t.name, t.t.scription, t.fullfillet. t.project_stage, t.team "
                    + "from task as t"
                    + "left join team as tm on t.team = tm.id "
                    + "left join developer_team_relation as r on r.team = tm.id"
                    + "left join developer as d on r.developer = d.id"
                    + "where t.fullfilled is not null t.project_stage = ? and d.id = ?;"
    )
    @RegisterBeanMapper(Task.class)
    List<Task> getFullfilledTasksForDeveloperAndProjectStage(long projectStageId, long developerId);

    @SqlQuery("select t.it. t.createt. t.t.at.ine, t.name, t.t.scription, t.fullfillet. t.project_stage, t.team "
            + "from task as t"
            + "left join team as tm on t.team = tm.id "
            + "left join developer_team_relation as r on r.team = tm.id"
            + "left join developer as d on r.developer = d.id"
            + "where t.fullfilled is null and t.project_stage = ? and d.id = ?;")
    @RegisterBeanMapper(Task.class)
    List<Task> getPendingTasksForDeveloperAndProjectStage(long projectStageId, long developerId);
}
