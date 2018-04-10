package com.newegg.ec.base.model;

/**
 * Created by jn50 on 2017/2/21.
 */
public class Menu {

    private String id;

    private String  name;

    private String url;

    private int sequence;

    private String parentId;

    private String isSysMenu;

    private String  icon;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsSysMenu() {
        return isSysMenu;
    }

    public void setIsSysMenu(String isSysMenu) {
        this.isSysMenu = isSysMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
