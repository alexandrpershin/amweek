package com.endava.androidamweek.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Алекс on 21.04.2017.
 */

public class Speaker {

    private Integer imageid;
    private String longinfo;
    private String name;
    private String shortinfo;
    private  ArrayList<SpeakerTrainings> training;



    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public String getLonginfo() {
        return longinfo;
    }

    public void setLonginfo(String longinfo) {
        this.longinfo = longinfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortinfo() {
        return shortinfo;
    }

    public void setShortinfo(String shortinfo) {
        this.shortinfo = shortinfo;
    }

//    public HashMap<String, String> getTrainings() {
//        return trainings;
//    }

    public ArrayList<SpeakerTrainings> getTraining() {
        return training;
    }

    public void setTraining(ArrayList<SpeakerTrainings> training) {
        this.training = training;
    }


//    public ArrayList<SpeakerTrainings> getTrainings() {
//        return trainings;
//    }
}
