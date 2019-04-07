package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;
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
    @SqlQuery("insert into developer " + "(name, email, pw_hash) values " + "(:name, :email, :pwhash) returning id;"
    )
    int insertDeveloper(@BindBean Developer d);


    /**
     * Updates an existing developer
     *
     * @param d
     * @return
     */
    @SqlQuery("update developer set "
            + "name = :name" + ", email = :email " + ", pw_hash = :pwhash "
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
     * Get the group of a developer
     *
     * @param id
     * @return String group name
     */
    @SqlQuery("select g.name from "
            + "developer d join developer_group_relation dg on d.id = dg.developer "
            + "join \"group\" g on dg.group = g.id "
            + "where d.id = ?"
    )
    @RegisterBeanMapper(Developer.class)
    List<String> getGroup(long id);

    /**
     * Inserts a developer into a permission group
     * @param devId the developer
     * @param groupName the permission group
     * @return the id of the record
     */
    @SqlQuery("insert into developer_group_relation (developer,\"group\") "
               + "values (?,(select id from \"group\" where name = ?)) "
               + "returning id;")
    int addToGroup(long devId, String groupName);

    /**
     * Deletes a developer from a group
     * @param devId
     * @param groupName
     * @return the former id of the record
     */
    @SqlQuery("delete from developer_group_relation where developer = ? and \"group\" = "
              + "(select id from \"group\" where name = ?) "
              + "returning id;")
    int removeFromGroup(long devId, String groupName);

    /**
     * Queries all teams of a developer
     *
     * @param d developer
     * @return all teams
     */
    @SqlQuery("select distinct t.* "
            + "from team as t join developer_team_relation as dtr on t.id = dtr.team "
            + "where dtr.developer = :id;")
    @RegisterBeanMapper(Team.class)
    List<Team> getTeams(@BindBean Developer d);

    /**
     * Queries all tasks of a developer
     *
     * @param d developer
     * @return all tasks
     */
    @SqlQuery("select distinct ta.* from task as ta, developer_team_relation as dtr" +
            " where dtr.developer = :id and dtr.team = ta.team")
    @RegisterBeanMapper(Task.class)
    List<Task> getTasks(@BindBean Developer d);
}
