package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
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
}
