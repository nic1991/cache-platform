package com.newegg.ec.cache.model;

import com.newegg.ec.cache.core.mysql.MysqlField;
import com.newegg.ec.cache.core.mysql.MysqlTable;
/**
 * Created by gl49 on 2018/4/20.
 */
@MysqlTable( name = "cluster", autoCreate = true)
public class Cluster {
    @MysqlField(isPrimaryKey = true, field = "id", type = "int")
    private int id;
    @MysqlField(field = "user_group", type = "varchar(30)", notNull = true)
    private String userGroup;
    @MysqlField(field = "ip", type = "varchar(25)", notNull = true)
    private String ip;
    @MysqlField(field = "port", type = "int", notNull = true)
    private int port;
    @MysqlField(field = "ssl_username", type = "varchar(64)", notNull = false)
    private String sslUsername;
    @MysqlField(field = "ssl_password", type = "varchar(64)", notNull = false)
    private String sslPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", userGroup='" + userGroup + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", sslUsername='" + sslUsername + '\'' +
                ", sslPassword='" + sslPassword + '\'' +
                '}';
    }
}
