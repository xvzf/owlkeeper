package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

public class TaskComment implements HasID{
    long id;
    Timestamp created;
    String content;
    long developer;
    long task;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDeveloper() {
        return developer;
    }

    public void setDeveloper(long developer) {
        this.developer = developer;
    }

    public long getTask() {
        return task;
    }

    public void setTask(long task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskComment{" +
                "id=" + id +
                ", created=" + created +
                ", content='" + content + '\'' +
                ", developer=" + developer +
                ", task=" + task +
                '}';
    }
}
