package com.newegg.ec.cache.plugin.humpback;

import com.newegg.ec.cache.core.mysql.MysqlField;
import com.newegg.ec.cache.core.mysql.MysqlTable;

/**
 * Created by gl49 on 2018/4/22.
 */
@MysqlTable( name = "humpback_node", autoCreate = true)
public class HumpbackNode {
    @MysqlField(isPrimaryKey = true, field = "id", type = "int")
    private int id;
    @MysqlField(field = "image", type = "varchar(250)", notNull = true)
    private String image;
    @MysqlField(field = "container_name", type = "varchar(40)", notNull = true)
    private String containerName;
    @MysqlField(field = "cluster_name", type = "varchar(25)", notNull = true)
    private String clusterName;
    @MysqlField(field = "user_group", type = "varchar(25)", notNull = true)
    private String group;
    @MysqlField(field = "ip", type = "varchar(25)", notNull = true)
    private String ip;
    @MysqlField(field = "port", type = "smallint", notNull = true)
    private int port;
    @MysqlField(field = "add_time", type = "int",  notNull = true)
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

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
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

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "HumpbackNode{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", containerName='" + containerName + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", group='" + group + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", addTime=" + addTime +
                '}';
    }
}
