package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;


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
    @SqlQuery("delete from project_stage where id = ? returning id;"
    )
    @RegisterBeanMapper(ProjectStage.class)
    int removeProjectStage(long id);
}
