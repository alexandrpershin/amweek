package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Speaker implements Serializable {
    private static final long serialVersionUID = -7874823823497497357L;

    public Speaker() {
    }

    @DatabaseField()
    private Integer id;

    @DatabaseField()
    private String imageId;

    @DatabaseField()
    private String longInfo;

    @DatabaseField()
    private String shortInfo;

    @DatabaseField()
    private String name;


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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
