package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

/**
 * Developer Container
 */
public class Developer extends HasID {
    private long id;
    private Timestamp created;
    private String name;
    private String email;
    private String pwhash;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwhash() {
        return pwhash;
    }

    public void setPwhash(String pwhash) {
        this.pwhash = pwhash;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Developer)) return false;

        Developer other = (Developer) o;

        return other.getId() == this.getId();
    }

}
