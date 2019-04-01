package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

public class ProjectStage extends HasID {
    long id;
    Timestamp created;
    String name;
    long project;
    long index;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ProjectStage{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", index=" + index +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProjectStage)) return false;

        ProjectStage other = (ProjectStage) o;

        return other.getId() == this.getId();
    }
}
