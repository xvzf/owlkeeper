package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Team;
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
     * Updates an existing project
     *
     * @param p
     * @return
     */
    @SqlQuery("update project set "
            + "name = :name"
            + ", description = :description"
            + ", type = :type"
            + " where id = :id returning id;"
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

    /**
     * Gets all Teams working on a project
     *
     * @param id id of the project
     * @return a list with all Teams working on the project
     */

    @SqlQuery("select t.id, t.created, t.name, t.leader " +
            "from team as t " +
            "left join team_project_relation as tpr on t.id = tpr.team " +
            "where tpr.project = ?;")
    @RegisterBeanMapper(Team.class)
    List<Team> getTeams(long id);

    /**
     * Gets the projects a user is assigned to.
     *
     * @param id the id of the developer
     * @return a list with the projects he is assigned to
     */
    @SqlQuery("SELECT p.id, p.created, p.name, p.description, p.type " +
            "FROM project AS p " +
            "JOIN team_project_relation AS tpr ON p.id = tpr.project " +
            "JOIN developer_team_relation AS dtr ON dtr.team = tpr.team " +
            "WHERE dtr.developer = ?;")
    @RegisterBeanMapper(Project.class)
    List<Project> getProjectsOfUser(long id);

    /**
     * Explicitly assigns a team to a project
     */
    @SqlQuery("insert into team_project_relation (project, team) " + "values (?,?) on conflict do nothing returning id;")
    int assignTeamToProject(long projectId, long teamId);
}
