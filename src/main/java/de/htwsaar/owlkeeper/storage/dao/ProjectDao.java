package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
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
}
