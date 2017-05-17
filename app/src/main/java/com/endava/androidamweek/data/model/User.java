package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
@DatabaseTable
public class User implements Serializable {

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
    private ArrayList<UserTraining> trainings;

    @DatabaseField
    private String firebaseFieldName;

    public User() {
    }

    public String getFirebaseFieldName() {
        return firebaseFieldName;
    }

    public void setFirebaseFieldName(String firebaseFieldName) {
        this.firebaseFieldName = firebaseFieldName;
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
        return trainings;
    }

    public void setTrainings(ArrayList<UserTraining> training) {
        this.trainings = training;
    }
}
