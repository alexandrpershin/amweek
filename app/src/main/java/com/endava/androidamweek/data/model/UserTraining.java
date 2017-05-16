
package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class UserTraining implements Serializable {
    @DatabaseField
    private Integer trainingId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private User user;

    @DatabaseField
    private String firebaseFieldName;

    public UserTraining() {
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public String getFirebaseFieldName() {
        return firebaseFieldName;
    }

    public void setFirebaseFieldName(String firebaseFieldName) {
        this.firebaseFieldName = firebaseFieldName;
    }
}
