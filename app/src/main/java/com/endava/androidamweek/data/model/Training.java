package com.endava.androidamweek.data.model;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Training implements Serializable, Comparable<Training> {
    private static final long serialVersionUID = -7874823823497497347L;
    @DatabaseField
    private String date;
    @DatabaseField
    private Integer day;
    @DatabaseField
    private Integer id;
    @DatabaseField
    private Integer targetAudience;
    @DatabaseField
    private Integer speakerId;
    @DatabaseField
    private String location;
    @DatabaseField
    private Boolean remoteCall;
    @DatabaseField
    private String timeStart;
    @DatabaseField
    private String timeEnd;
    @DatabaseField
    private String title;
    @DatabaseField
    private String language;
    @DatabaseField
    private String description;
    @DatabaseField
    private String stream;
    @DatabaseField
    private String type;

    public Training() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(Integer targetAudience) {
        this.targetAudience = targetAudience;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRemoteCall() {
        return remoteCall;
    }

    public void setRemoteCall(Boolean remoteCall) {
        this.remoteCall = remoteCall;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Training o) {
        String compareTime = ((Training) o).getTimeStart();

        return this.getTimeStart().compareTo(compareTime);
    }
}
