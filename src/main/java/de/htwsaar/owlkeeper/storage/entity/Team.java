package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

public class Team extends HasID {
    private long id;
    private Timestamp created;
    private String name;
    private long leader;

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

    public long getLeader() {
        return leader;
    }

    public void setLeader(long leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", leader=" + leader +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Team)) return false;

        Team other = (Team) o;

        return other.getId() == this.getId();
    }
}
