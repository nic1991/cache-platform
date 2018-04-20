package com.newegg.ec.cache.model;

/**
 * Created by gl49 on 2018/4/20.
 */
public class Cluster {
    private String userGroup;
    private String ip;
    private int port;
    private String sslUsername;
    private String sslPassword;

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSslUsername() {
        return sslUsername;
    }

    public void setSslUsername(String sslUsername) {
        this.sslUsername = sslUsername;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }
}
