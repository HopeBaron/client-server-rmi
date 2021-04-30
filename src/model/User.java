package model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public final class User implements Serializable {
    private final long id;
    private String username;
    private String password;
    private Permissions permission;
    private Instant registrationDate;

    public User(long id, String username, String password, Permissions permission, Instant registrationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.registrationDate = registrationDate;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permissions getPermission() {
        return permission;
    }

    public Instant getRegistartionDate() {
        return registrationDate;
    }

    public void setRegistartionDate(Instant registartionDate) {
        this.registrationDate = registartionDate;
    }
}
