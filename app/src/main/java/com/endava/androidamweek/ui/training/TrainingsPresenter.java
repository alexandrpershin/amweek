package com.endava.androidamweek.ui.training;

import com.endava.androidamweek.data.callbacks.TrainingCallback;

class TrainingsPresenter {

    void loadTrainings(TrainingCallback trainingCallback){
        TrainingAsyncTask trainingAsyncTask= new TrainingAsyncTask(trainingCallback);
        trainingAsyncTask.execute();
    }
}
