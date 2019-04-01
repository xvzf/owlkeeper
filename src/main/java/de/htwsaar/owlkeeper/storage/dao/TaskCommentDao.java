package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
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
     * Queries comments sorted by task and created
     *
     * @return
     */
    @SqlQuery("select * from task_comment order by task, created ASC;")
    @RegisterBeanMapper(TaskComment.class)
    List<TaskComment> getCommentsSorted();

    /**
     * Queries comments for task
     *
     * @param id
     * @return
     */
    @SqlQuery("select * from task_comment where task_comment.task = ?;")
    @RegisterBeanMapper(TaskComment.class)
    List<TaskComment> getCommentsForTask(long id);

    /**
     * Inserts a new TaskComment into the database
     *
     * @param p
     * @return Inserted id
     */
    @SqlQuery("insert into task_comment (content, developer, task) values (:content, :developer, :task) returning id;"
    )
    int insertTaskComment(@BindBean TaskComment p);

    /**
     * Updates an existing task comment
     *
     * @param p
     * @return
     */
    @SqlQuery("update task_comment set "
            + "content = :content"
            + ", developer = :developer"
            + ", task = :task"
            + " where id = :id returning id;"
    )
    int updateTaskComment(@BindBean TaskComment p);

    /**
     * Removes a task comment from the database
     *
     * @param id
     * @return Removed id
     */
    @SqlQuery("delete from task_comment where id = ? returning id;")
    @RegisterBeanMapper(TaskComment.class)
    int deleteTaskComment(long id);
}
