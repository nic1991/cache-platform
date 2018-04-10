package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * Created by lf52 on 2018/3/16.
 */
public class Machine implements Serializable {

    private String ip;
    private String machineName;
    private String usename;
    private String passwd;
    private String location;
    private int memory;
    private int coreSize;
    //是否是虚拟机  0 : 否 1 ： 是
    private String isVM;
    //宿主机ip
    private String hostIp;
    //是否开启机器状态收集 0 : 否 1 ： 是
    private String isMonitor;
    //机器说明
    private String remark;
    private String score;
    private Integer warningNum;
    private String updateTime;

    public Machine(){
        this.warningNum = 0;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public String getIsVM() {
        return isVM;
    }

    public void setIsVM(String isVM) {
        this.isVM = isVM;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(String isMonitor) {
        this.isMonitor = isMonitor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getWarningNum() {
        return warningNum;
    }

    public void setWarningNum(Integer warningNum) {
        this.warningNum = warningNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "ip='" + ip + '\'' +
                ", machineName='" + machineName + '\'' +
                ", usename='" + usename + '\'' +
                ", passwd='" + passwd + '\'' +
                ", location='" + location + '\'' +
                ", memory=" + memory +
                ", coreSize=" + coreSize +
                ", isVM='" + isVM + '\'' +
                ", hostIp='" + hostIp + '\'' +
                ", isMonitor='" + isMonitor + '\'' +
                ", remark='" + remark + '\'' +
                ", score='" + score + '\'' +
                ", warningNum=" + warningNum +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
