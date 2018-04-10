package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * Created by lf52 on 2018/3/17.
 */
public class MachineNetWorkInfo implements Serializable {

    private String ip;
    private String rxpck;//每秒钟接收的数据包
    private String txpck;//每秒钟发送的数据包
    private String rxbyt;//每秒钟接收的字节数
    private String txbyt;//每秒钟发送的字节数
    private String rxcmp;//每秒钟接收的压缩数据包
    private String txcmp;//每秒钟发送的压缩数据包
    private String rxmcst;//每秒钟接收的多播数据包
    private String updateTime;

    public String getRxpck() {
        return rxpck;
    }

    public void setRxpck(String rxpck) {
        this.rxpck = rxpck;
    }

    public String getTxpck() {
        return txpck;
    }

    public void setTxpck(String txpck) {
        this.txpck = txpck;
    }

    public String getRxbyt() {
        return rxbyt;
    }

    public void setRxbyt(String rxbyt) {
        this.rxbyt = rxbyt;
    }

    public String getTxbyt() {
        return txbyt;
    }

    public void setTxbyt(String txbyt) {
        this.txbyt = txbyt;
    }

    public String getRxcmp() {
        return rxcmp;
    }

    public void setRxcmp(String rxcmp) {
        this.rxcmp = rxcmp;
    }

    public String getTxcmp() {
        return txcmp;
    }

    public void setTxcmp(String txcmp) {
        this.txcmp = txcmp;
    }

    public String getRxmcst() {
        return rxmcst;
    }

    public void setRxmcst(String rxmcst) {
        this.rxmcst = rxmcst;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "MachineNetWorkInfo{" +
                "ip='" + ip + '\'' +
                ", rxpck='" + rxpck + '\'' +
                ", txpck='" + txpck + '\'' +
                ", rxbyt='" + rxbyt + '\'' +
                ", txbyt='" + txbyt + '\'' +
                ", rxcmp='" + rxcmp + '\'' +
                ", txcmp='" + txcmp + '\'' +
                ", rxmcst='" + rxmcst + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
