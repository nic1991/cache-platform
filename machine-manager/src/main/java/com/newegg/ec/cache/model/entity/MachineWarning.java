package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * 主机预警信息
 * @author Jay.H.Zou
 * @date 2018/3/29
 */
public class MachineWarning implements Serializable {
    private String id;
    private String ip;
    private String limitName;
    private String formula;
    private String grade;
    private String description;
    private String data;
    private Integer isChecked;
    private String updateTime;

    public MachineWarning(){
        // 0:未检查过 1:检查过
        this.isChecked = 0;
    }

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MachineWarning{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", limitName='" + limitName + '\'' +
                ", formula='" + formula + '\'' +
                ", grade='" + grade + '\'' +
                ", description='" + description + '\'' +
                ", data='" + data + '\'' +
                ", isChecked=" + isChecked +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
