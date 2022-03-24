package de.desktop.application.kretzschmar_desktop.data.user;

import java.io.Serializable;

public class User implements Serializable {
    protected int id;
    protected String username;
    protected String password;
    protected UserRights rights;

    public User() { }

    public User(String username, String password, UserRights rights) {
        this.username = username;
        this.password = password;
        this.rights = rights;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRights(UserRights rights) {
        this.rights = rights;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRights getRights() {
        return rights;
    }
}
