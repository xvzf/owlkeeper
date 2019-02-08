package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface ProjectDao {

    /**
     * Get all projects
     *
     * @return List of projects
     */
    @SqlQuery("select * from project;")
    @RegisterBeanMapper(Project.class)
    List<Project> getProjects();

    /**
     * Gets project stages for a project
     *
     * @param id project id
     * @return
     */
    @SqlQuery("select * from project_stage s where project = ? order by index asc;")
    @RegisterBeanMapper(ProjectStage.class)
    List<ProjectStage> getStagesForProject(long id);

    /**
     * Gets a project
     *
     * @param id project id
     * @return
     */
    @SqlQuery("select * from project where id = ?;")
    @RegisterBeanMapper(Project.class)
    Project getProject(long id);

    /**
     * Inserts a new Project into the database
     *
     * @param p
     * @return Inserted id
     */
    @SqlQuery("insert into project "
            + "(name, description, type) values "
            + "(:name, :description, :type) returning id;"
    )
    int insertProject(@BindBean Project p);

    /**
     * Updates an existing developer
     *
     * @param p
     * @return
     */
    @SqlQuery("update project set "
            + "name = :name"
            + ", role = :role"
            + ", description = :description"
            + ", type = :type"
            + "where id = :id returning id;"
    )
    int updateProject(@BindBean Project p);

    /**
     * Removes a project from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from project "
            + "where id = ? returning id;"
    )
    @RegisterBeanMapper(Project.class)
    int deleteProject(long id);
}
