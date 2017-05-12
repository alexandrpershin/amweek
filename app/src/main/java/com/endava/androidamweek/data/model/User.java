package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@DatabaseTable
public class User {

    @DatabaseField
    private String email;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField
    private String id;

    @DatabaseField (generatedId = true)
    private Integer userId;

    @DatabaseField(dataType= DataType.SERIALIZABLE)
    private ArrayList<UserTraining> training;

    public User() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<UserTraining> getTraining() {
        return training;
    }

    public void setTraining(ArrayList<UserTraining> training) {
        this.training = training;
    }
}
