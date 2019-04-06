package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

public class Task extends HasID {
    long id;
    Timestamp created;
    Timestamp deadline;
    String name;
    String description;
    Timestamp fulfilled;
    long projectStage;
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

    public Timestamp getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Timestamp fulfilled) {
        this.fulfilled = fulfilled;
    }

    public long getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(long projectStage) {
        this.projectStage = projectStage;
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
                ", fulfilled=" + fulfilled +
                ", projectStage=" + projectStage +
                ", team=" + team +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Task)) return false;

        Task other = (Task) o;

        return other.getId() == this.getId();
    }
}
