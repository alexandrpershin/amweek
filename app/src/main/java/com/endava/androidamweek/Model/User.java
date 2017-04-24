package com.endava.androidamweek.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алекс on 21.04.2017.
 */

public class User {
    private String email;
    private String name;
    private ArrayList<SpeakerTrainings> training;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SpeakerTrainings> getTraining() {
        return training;
    }

    public void setTraining(ArrayList<SpeakerTrainings> training) {
        this.training = training;
    }
}
