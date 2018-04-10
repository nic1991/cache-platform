package com.newegg.ec.cache.model.entity;

import java.io.Serializable;

/**
 * Created by lf52 on 2018/3/22.
 */
public class SystemConfig implements Serializable {

    private String conf_key;

    private String conf_value;

    private String info;

    private String type;

    public String getConf_key() {
        return conf_key;
    }

    public void setConf_key(String conf_key) {
        this.conf_key = conf_key;
    }

    public String getConf_value() {
        return conf_value;
    }

    public void setConf_value(String conf_value) {
        this.conf_value = conf_value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "conf_key='" + conf_key + '\'' +
                ", conf_value='" + conf_value + '\'' +
                ", info='" + info + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
