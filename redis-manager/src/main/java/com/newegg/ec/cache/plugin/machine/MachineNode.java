package com.newegg.ec.cache.plugin.machine;

/**
 * Created by gl49 on 2018/4/22.
 */
public class MachineNode {
    private int id;
    private String image;
    private String clusterName;
    private String group;
    private String username;
    private String password;
    private String ip;
    private int port;
    private String startService;
    private String stopService;
    private String installPath;
    private int addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getStartService() {
        return startService;
    }

    public void setStartService(String startService) {
        this.startService = startService;
    }

    public String getStopService() {
        return stopService;
    }

    public void setStopService(String stopService) {
        this.stopService = stopService;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }
}
