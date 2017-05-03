package com.endava.androidamweek.ui.training;

import android.os.AsyncTask;
import com.endava.androidamweek.data.callbacks.TrainingCallback;
import com.endava.androidamweek.data.model.Database;

public class TrainingAsyncTask extends AsyncTask <Void,Void,Void>{
    private TrainingCallback trainingCallback;

    public TrainingAsyncTask(TrainingCallback trainingCallback ) {
        this.trainingCallback=trainingCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (Database.getInstance().getSpeakers()==null && Database.getInstance().getTrainings()==null) {
        }
            return null;

        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        trainingCallback.onSuccessResponse(Database.getInstance().getTrainings(),Database.getInstance().getSpeakers());
    }
}
