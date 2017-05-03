package com.endava.androidamweek.data.callbacks;

import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;

import java.util.List;

public interface TrainingCallback {

    void onSuccessResponse(List<Training> trainings, List<Speaker> speakers);
    void onErrorResponse(String errorMessage);
}
