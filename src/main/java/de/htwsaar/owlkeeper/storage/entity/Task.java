package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

public class Task {
    long id;
    Timestamp created;
    Timestamp deadline;
    String name;
    String description;
    Timestamp fullfilled;
    long project_stage;
    long team;

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

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getFullfilled() {
        return fullfilled;
    }

    public void setFullfilled(Timestamp fullfilled) {
        this.fullfilled = fullfilled;
    }

    public long getProject_stage() {
        return project_stage;
    }

    public void setProject_stage(long project_stage) {
        this.project_stage = project_stage;
    }

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", created=" + created +
                ", deadline=" + deadline +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fullfilled=" + fullfilled +
                ", project_stage=" + project_stage +
                ", team=" + team +
                '}';
    }
}
