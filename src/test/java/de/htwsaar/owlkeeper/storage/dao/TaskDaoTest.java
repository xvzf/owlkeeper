package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDaoTest {

    @Test
    void testGetTask() {

        Task task = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getTask(1);
        });

        assertEquals(1, task.getId());
        assertEquals("Task 1", task.getName());
        assertEquals("Task 1 description", task.getDescription());
        assertEquals(1, task.getProjectStage());
        assertNull(task.getFulfilled());
    }

    @Test
    void testGetTasksForProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getTasksForProjectStage(1);
        });

        assertEquals(2, tasks.size());
        tasks.forEach(task -> assertEquals(1, task.getProjectStage()));
    }

    @Test
    void testGetFulfilledTasksForProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getFulfilledTasksforProjectStage(2);
        });

        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertNotNull(task.getFulfilled());
        assertEquals(3, task.getId());
    }

    @Test
    void testGetPendingTasksForProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getPendingTasksForProjectStage(1);
        });

        assertEquals(2, tasks.size());
        Task task = tasks.get(0);
        assertNull(task.getFulfilled());
        assertEquals(1, task.getId());
        task = tasks.get(1);
        assertNull(task.getFulfilled());
        assertEquals(2, task.getId());
    }

    @Test
    void testGetTasksForDeveloperAndProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getTasksForDeveloperAndProjectStage(1, 1);
        });

        assertEquals(1, tasks.size());
        tasks.forEach(task -> assertEquals(1, task.getProjectStage()));
    }

    @Test
    void testGetFulfilledTasksForDeveloperAndProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getFulfilledTasksForDeveloperAndProjectStage(2, 3);
        });

        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertNotNull(task.getFulfilled());
        assertEquals(3, task.getId());
    }

    @Test
    void testGetPendingTasksForDeveloperAndProjectStage() {

        List<Task> tasks = DBConnection.getJdbi().withExtension(TaskDao.class, dao -> {
            return dao.getPendingTasksForDeveloperAndProjectStage(1, 1);
        });

        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertNull(task.getFulfilled());
        assertEquals(2, task.getId());
    }

}