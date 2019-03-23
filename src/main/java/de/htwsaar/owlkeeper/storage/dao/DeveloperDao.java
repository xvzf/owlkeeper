package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
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
            + "(name, email) values "
            + "(:name, :email) returning id;"
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
            + ", email = :email"
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
}
