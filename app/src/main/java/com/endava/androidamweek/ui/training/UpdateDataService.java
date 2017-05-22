package com.endava.androidamweek.ui.training;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.ui.quizz.QuizzActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UpdateDataService extends Service {
    private ExecutorService executorService;
    private Notify notify;
    private Boolean isFirsTimeRunning;
    private Boolean stop;
    private int childrenCount;

    public boolean isDatabaseNull() {
        return (Database.getInstance().getSpeakers() == null || Database.getInstance().getTrainings() == null
                || Database.getInstance().getUsers() == null || Database.getInstance().getQuizzes() == null);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isFirsTimeRunning = true;

        childrenCount = LocalDatabase.getInstance().getQuizzes().size();

        executorService.execute(notify);
        Log.i("UpdateDataService", "onStartCommand");
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.i("UpdateDataService", "onDestroy");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Database.getInstance();
        notify = new Notify();
        executorService = Executors.newFixedThreadPool(1);
        isFirsTimeRunning = true;
        stop = false;
        Log.i("UpdateDataService", "onCreate");
    }

    private void sendMessage() {
        while (isDatabaseNull()) {
        }

        LocalDatabase.getInstance().updataData();

        childrenCount = LocalDatabase.getInstance().getQuizzes().size();

        Intent intent = new Intent("UpdateData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        isFirsTimeRunning = false;
    }


    class Notify implements Runnable {

        @Override
        public void run() {
            databaseValueEventListener();
            quizzChildEventListener();
            Log.i("UpdateDataService", "run");
        }
    }

    private void quizzChildEventListener() {
        Database.getInstance().getQuizzesReferece().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!isFirsTimeRunning) {
                    if (dataSnapshot.getChildrenCount() > childrenCount) {
                        Intent intent = new Intent(getApplicationContext(), QuizzActivity.class);
                        intent.setFlags(Intent.FLAG_FROM_BACKGROUND);

                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.ic_fill_star)
                                .setAutoCancel(true)
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                .setVibrate(new long[]{1000, 1000})
                                .setContentTitle("New quizz!");
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, builder.build());

                        sendMessage();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void databaseValueEventListener() {
        Database.getInstance().getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!stop) {
                    Log.i("UpdateDataService", " databaseValueEventListener sendMessage");

                    sendMessage();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
