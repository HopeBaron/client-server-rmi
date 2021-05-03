package common.model;

import java.io.Serializable;
import java.time.Instant;

public final class User implements Serializable {
    private long id;
    private String username;
    private String password;
    private Permissions permission;
    private final Instant registrationDate;
    private boolean active;

    public User(long id, String username, String password, Permissions permission, Instant registrationDate, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.registrationDate = registrationDate;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean activeState) {
        this.active = activeState;
    }
}
