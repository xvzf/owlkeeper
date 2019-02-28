package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
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
    @SqlQuery("insert into team "
            + "(name, leader) values "
            + "(:name, :leader) returning id;"
    )
    int insertTeam(@BindBean Team t);


    /**
     * Updates an existing team
     *
     * @param t
     * @return
     */
    @SqlQuery("update team set "
            + "name = :name"
            + ", leader = :leader"
            + "where id = :id returning id;"
    )
    int updateTeam(@BindBean Team t);


    /**
     * Removes a team from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from team "
            + "where id = ?"
            + "returning id;"
    )
    @RegisterBeanMapper(Team.class)
    int deleteTeam(long id);


}
