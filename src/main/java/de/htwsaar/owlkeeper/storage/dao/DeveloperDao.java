package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface DeveloperDao {

    /**
     * Get all developers
     *
     * @return List of developers
     */
    @SqlQuery("select * from developer;")
    @RegisterBeanMapper(Developer.class)
    List<Developer> getDevelopers();

    /**
     * Get developer by id
     *
     * @return
     */
    @SqlQuery("select * from developer where id = ?")
    @RegisterBeanMapper(Developer.class)
    Developer getDeveloper(long id);

    /**
     * Get developer by email
     *
     * @return
     */
    @SqlQuery("select * from developer where email = ?")
    @RegisterBeanMapper(Developer.class)
    Developer getDeveloper(String email);

    /**
     * Inserts a new developer into the database
     *
     * @param d
     * @return Inserted id
     */
    @SqlQuery("insert into developer "
            + "(name, role, email, chief) values "
            + "(:name, :role, :email, :chief) returning id;"
    )
    int insertDeveloper(@BindBean Developer d);


    /**
     * Updates an existing developer
     *
     * @param d
     * @return
     */
    @SqlQuery("update developer set "
            + "name = :name"
            + ", role = :role"
            + ", email = :email"
            + ", is_chief = :chief "
            + "where id = :id returning id;"
    )
    int updateDeveloper(@BindBean Developer d);


    /**
     * Removes a developer from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from developer "
            + "where id = ?"
            + "returning id;"
    )
    @RegisterBeanMapper(Developer.class)
    int deleteDeveloper(long id);

    /**
     * Queries all teams of a developer
     *
     * @param d developer
     * @return all teams
     */
    @SqlQuery("select distinct  t.id as id, t.created as created, t.name as name, t.leader as leader " +
            " from team as t, developer_team_relation as dtr where dtr.developer = :id;")
    @RegisterBeanMapper(Team.class)
    List<Team> getTeams(@BindBean Developer d);
}
