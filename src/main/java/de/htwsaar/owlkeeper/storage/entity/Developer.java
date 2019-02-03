package de.htwsaar.owlkeeper.storage.entity;

import java.sql.Timestamp;

/**
 * Developer Container
 */
public class Developer implements IDable {
    private long id;
    private Timestamp created;
    private String name;
    private String role;
    private String email;
    private String pwhash;
    private boolean chief;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", pwhash='" + pwhash + '\'' +
                ", chief=" + chief +
                '}';
    }
}
