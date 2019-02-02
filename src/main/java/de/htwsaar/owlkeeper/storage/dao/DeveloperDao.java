package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

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
     * Inserts a new developer into the database
     *
     * @param d
     * @return Inserted id
     */
    @SqlUpdate("insert into developer "
            + "(name, role, email, pwhash, chief) values "
            + "(:name, :role, :email, :pwhash, :chief) returning id;"
    )
    long insertDeveloper(@BindBean Developer d);


    /**
     * Updates an existing developer
     *
     * @param d
     * @return
     */
    @SqlUpdate("update developer set "
            + "name = :name "
            + "role = :role "
            + "email = :email "
            + "pwhash = :pwhash "
            + "chief = :chief "
            + "where id = :id returning id;"
    )
    long updateDeveloper(@BindBean Developer d);

}
