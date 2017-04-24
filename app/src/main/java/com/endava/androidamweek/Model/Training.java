package com.endava.androidamweek.Model;

/**
 * Created by Алекс on 21.04.2017.
 */

public class Training {
    private String date;
    private Integer day;
    private String location;
    private String longinfo;
    private Integer numberofparticipants;
    private Boolean remotecall;
    private String shortinfo;
    private String time;
    private String Name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLonginfo() {
        return longinfo;
    }

    public void setLonginfo(String longinfo) {
        this.longinfo = longinfo;
    }

    public Integer getNumberofparticipants() {
        return numberofparticipants;
    }

    public void setNumberofparticipants(Integer numberofparticipants) {
        this.numberofparticipants = numberofparticipants;
    }

    public Boolean getRemotecall() {
        return remotecall;
    }

    public void setRemotecall(Boolean remotecall) {
        this.remotecall = remotecall;
    }

    public String getShortinfo() {
        return shortinfo;
    }

    public void setShortinfo(String shortinfo) {
        this.shortinfo = shortinfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
