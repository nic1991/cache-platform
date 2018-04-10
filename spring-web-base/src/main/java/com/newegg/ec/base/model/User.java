package com.newegg.ec.base.model;

import net.sf.json.JSONArray;

/**
 * Created by jn50 on 2016/4/13.
 */
public class User {

    private String id;//唯一id
    private String name; // 唯一名称
    private String password;//密码
    private JSONArray roles;//角色

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONArray getRoles() {
        return roles;
    }

    public void setRoles(JSONArray roles) {
        this.roles = roles;
    }


}
