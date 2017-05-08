package com.endava.androidamweek.ui.training;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.endava.androidamweek.data.model.Database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class UpdateDataService extends Service {
    ExecutorService executorService;



    public class LocalBinder extends Binder {
        UpdateDataService getService() {
            return UpdateDataService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notify notify= new Notify();
        executorService.execute(notify);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Database.getInstance();

        executorService= Executors.newFixedThreadPool(1);

    }

    private void sendMessage( ) {
        Intent intent = new Intent("UpdateData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    class Notify implements Runnable{

        @Override
        public void run() {
                makeRequest();
        }

    }


    public void makeRequest() {
        while (Database.getInstance().getSpeakers()==null && Database.getInstance().getTrainings()==null
                && Database.getInstance().getUsers()==null && Database.getInstance().getQuizzes()==null) {
        }
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMessage();

    }

}
