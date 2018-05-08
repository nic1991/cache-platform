package com.newegg.ec.cache.plugin.humpback;

import com.newegg.ec.cache.plugin.basemodel.RemovePram;

/**
 * Created by gl49 on 2018/5/8.
 */
public class HumbackRemoveParam implements RemovePram{
    private String ip;
    private String containerId;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
