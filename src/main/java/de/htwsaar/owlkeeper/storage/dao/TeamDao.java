package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface TeamDao {

    /**
     * Query team by id
     *
     * @param id
     * @return
     */
    @SqlQuery("select * from team where id = ?;")
    @RegisterBeanMapper(Team.class)
    Team getTeam(long id);

    /**
     * Query all tasks of the team
     * @param id
     * @return
     */
    @SqlQuery("select * from task where team = ?;")
    @RegisterBeanMapper(Task.class)
    List<Task> getTasks(long id);

    /**
     * Query all teams
     *
     * @return
     */
    @SqlQuery("select * from team;")
    @RegisterBeanMapper(Team.class)
    List<Team> getTeams();

    /**
     * Get the list of developers who are members in a specified team
     *
     * @param teamId
     * @return
     */
    @SqlQuery(
            "select d.id, d.created, d.name, d.role, d.email, d.chief "
                    + "from developer_team_relation as tdr "
                    + "left join developer as d on tdr.developer = d.id "
                    + "where tdr.team = ?;"
    )
    @RegisterBeanMapper(Developer.class)
    List<Developer> getDevelopersPerTeam(long teamId);

    /**
     * Get teams a developer is member of
     *
     * @param developerId
     * @return
     */
    @SqlQuery(
            "select t.id, t.created, t.name, t.leader "
                    + "from developer_team_relation as tdr "
                    + "left join team as t on tdr.team = t.id "
                    + "where tdr.developer = ?;"
    )
    @RegisterBeanMapper(Team.class)
    List<Team> getTeamForDeveloper(long developerId);

    /**
     * Inserts a new team into the database
     *
     * @param t
     * @return Inserted id
     */
    @SqlQuery("insert into team (name, leader) values (:name, :leader) returning id;"
    )
    int insertTeam(@BindBean Team t);


    /**
     * Updates an existing team
     *
     * @param t
     * @return
     */
    @SqlQuery("update team set name = :name, leader = :leader where id = :id returning id;"
    )
    int updateTeam(@BindBean Team t);


    /**
     * Removes a team from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from team where id = ? returning id;"
    )
    @RegisterBeanMapper(Team.class)
    int deleteTeam(long id);

    /**
     * Inserts a new developer-team relation
     *
     * @param developerId
     * @param teamId
     * @return
     */
    @SqlQuery(
            "insert into developer_team_relation (developer, team) values(developerId, teamId) returning developer;"
    )
    @RegisterBeanMapper(Developer.class)
    long addDeveloper(long developerId, long teamId);

    /**
     * deletes a developer-team relation
     *
     * @param developerId
     * @param teamId
     * @return
     */
    @SqlQuery(
            "delete from developer_team_relation where developer = developerId and team = teamId returning developer;"
    )
    @RegisterBeanMapper(Developer.class)
    long removeDeveloper(long developerId, long teamId);

    /**
     * Retrieves leader
     *
     * @param id
     * @return leader
     */
    @SqlQuery("select leader from team where id = ?;"
    )
    @RegisterBeanMapper(Team.class)
    int getLeader(long id);
}
