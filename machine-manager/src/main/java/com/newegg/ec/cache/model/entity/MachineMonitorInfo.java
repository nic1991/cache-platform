package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * Created by lf52 on 2018/3/16.
 */
public class MachineMonitorInfo implements Serializable {

    private String ip;
    //os发行版本
    private String osVersion;
    //cpu型号
    private String cpuModel;
    //cpu使用
    private String cpuInfo;
    private String memory;
    private String loadAverage;
    private String swap;
    private String disk;
    //tcp连接数
    private int connectNum;
    //进程数
    private int psNum;
    //线程数
    private int treadNum;
    private String network;
    private String updateTime;
    //cpu使用率top3的进程
    private String topThree;

    private String logType;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getLoadAverage() {
        return loadAverage;
    }

    public void setLoadAverage(String loadAverage) {
        this.loadAverage = loadAverage;
    }

    public String getSwap() {
        return swap;
    }

    public void setSwap(String swap) {
        this.swap = swap;
    }

    public int getConnectNum() {
        return connectNum;
    }

    public void setConnectNum(int connectNum) {
        this.connectNum = connectNum;
    }

    public int getPsNum() {
        return psNum;
    }

    public void setPsNum(int psNum) {
        this.psNum = psNum;
    }

    public int getTreadNum() {
        return treadNum;
    }

    public void setTreadNum(int treadNum) {
        this.treadNum = treadNum;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTopThree() {
        return topThree;
    }

    public void setTopThree(String topThree) {
        this.topThree = topThree;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "MachineMonitorInfo{" +
                "ip='" + ip + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", cpuModel='" + cpuModel + '\'' +
                ", cpuInfo='" + cpuInfo + '\'' +
                ", memory='" + memory + '\'' +
                ", loadAverage='" + loadAverage + '\'' +
                ", swap='" + swap + '\'' +
                ", disk='" + disk + '\'' +
                ", connectNum=" + connectNum +
                ", psNum=" + psNum +
                ", treadNum=" + treadNum +
                ", network='" + network + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", topThree='" + topThree + '\'' +
                ", logType='" + logType + '\'' +
                '}';
    }
}
