package com.newegg.ec.base.model;

import net.sf.json.JSONArray;

/**
 * Created by jn50 on 2017/2/21.
 */
public class Role {

    private String id;

    private String name;

    private JSONArray menuArray;

    private JSONArray urlArray;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONArray getMenuArray() {
        return menuArray;
    }

    public void setMenuArray(JSONArray menuArray) {
        this.menuArray = menuArray;
    }

    public JSONArray getUrlArray() {
        return urlArray;
    }

    public void setUrlArray(JSONArray urlArray) {
        this.urlArray = urlArray;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
