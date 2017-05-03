package com.endava.androidamweek.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private Integer id;
    private ArrayList<UserTraining> training;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<UserTraining> getTraining() {
        return training;
    }

    public void setTraining(ArrayList<UserTraining> training) {
        this.training = training;
    }
}
