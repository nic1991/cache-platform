package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * 主机规则
 * @author Jay.H.Zou
 * @date 2018/3/29
 */
public class MachineRule implements Serializable {
    private String id;
    private String ip;
    private String limitName;
    // 公式
    private String formula;
    private String description;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MachineRule{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", limitName='" + limitName + '\'' +
                ", formula='" + formula + '\'' +
                ", description='" + description + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
