package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface TaskCommentDao {

    /**
     * Query task comment by id
     *
     */
    @SqlQuery("select * from task_comment where id = ?;")
    @RegisterBeanMapper(TaskComment.class)
    TaskComment getComment(long id);

    /**
     * Queries comments for task
     *
     * @param id
     * @return
     */
    @SqlQuery("select * from task_comment where task_comment.task = ?;")
    @RegisterBeanMapper(TaskComment.class)
    List<TaskComment> getCommentsForTask(long id);
}
