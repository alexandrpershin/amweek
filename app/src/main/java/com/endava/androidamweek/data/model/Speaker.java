package com.endava.androidamweek.data.model;

import java.io.Serializable;

public class Speaker implements Serializable{

    private Integer imageid;
    private Integer id;
    private String longInfo;
    private String shortInfo;
    private String name;


    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongInfo() {
        return longInfo;
    }

    public void setLongInfo(String longInfo) {
        this.longInfo = longInfo;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
