package de.htwsaar.owlkeeper.storage.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface AccessControlDao {

    @SqlQuery(
            "select exists ("
                    + "select * from task t "
                    + "join developer_team_relation dt on dt.team = t.team "
                    + "join developer d on dt.developer = d.id "
                    + " where d.id = ? and t.id = ?);"
    )
    Boolean isAssignedToTask(long developerId, long taskId);
}
