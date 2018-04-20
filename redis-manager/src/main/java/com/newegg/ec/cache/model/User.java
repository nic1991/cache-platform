package com.newegg.ec.cache.model;

/**
 * Created by gl49 on 2018/4/20.
 */
public class User {
    private String username;
    private String password;
    private String userGroup;

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

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userGroup='" + userGroup + '\'' +
                '}';
    }
}
