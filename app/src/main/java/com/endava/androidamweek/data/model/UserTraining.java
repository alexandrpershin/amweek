
package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
@DatabaseTable
public class UserTraining implements Serializable {
    @DatabaseField
    private Integer trainingId;

    @DatabaseField (foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private User user;

    public UserTraining() {
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
}
