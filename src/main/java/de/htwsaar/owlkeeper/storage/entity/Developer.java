package de.htwsaar.owlkeeper.storage.entity;

import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;

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

    public String getPwhash() throws InsufficientPermissionsException {
        //PermissionHandler.checkPermission(this::equals);
        // TODO Can't be here currently as it breaks logging in a new user.
        //  Possible solution: Pass the email from the DeveloperManager to the permissionHandler, then rewrite all checks
        //  to check only on email instead of id.
        return pwhash;
    }

    public void setPwhash(String pwhash) throws InsufficientPermissionsException {
        // PermissionHandler.checkPermission(this::equals); TODO
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
