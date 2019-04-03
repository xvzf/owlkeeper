package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;


public interface ProjectStageDao {

    /**
     * Gets a ProjectStage
     *
     * @param id ProjectStage id
     * @return
     */
    @SqlQuery("select * from project_stage where id = ?;")
    @RegisterBeanMapper(ProjectStage.class)
    ProjectStage getProjectStage(long id);

    /**
     * Inserts a new ProjectStage into the database
     *
     * @param p
     * @return Inserted id
     */
    @SqlQuery("insert into project_stage "
            + "(name, project, index) values "
            + "(:name, :project, :index) returning id;"
    )
    int insertProjectStage(@BindBean ProjectStage p);

    /**
     * Updates an existing ProjectStage
     *
     * @param p
     * @return
     */
    @SqlQuery("update project_stage set "
            + "created = :created"
            + "name = :name"
            + ", project = :project"
            + ", index = :index "
            + "where id = :id returning id;"
    )
    int updateProjectStage(@BindBean ProjectStage p);

    /**
     * Removes a ProjectStage from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from project_stage where id = ? returning id;")
    @RegisterBeanMapper(ProjectStage.class)
    int removeProjectStage(long id);

    /**
     * Retrieves all Tasks of the given ProjectStage
     *
     * @param id id of the ProjectStage
     * @return all Tasks
     */

    @SqlQuery("select * from task as t where t.project_stage = ? order by created, id;")
    @RegisterBeanMapper(Task.class)
    List<Task> getTasks(long id);

    /**
     * Returns the project that a stage is in.
     * @return
     */
    @SqlQuery("select p.id, p.created, p.name, p.description, p.type " +
            "from project_stage as ps join project as p on ps.project = p.id " +
            "where ps.id = ?;")
    @RegisterBeanMapper(Project.class)
    Project getProject(long id);
}
