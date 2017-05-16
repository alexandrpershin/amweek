package com.endava.androidamweek.ui.training;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.ui.quizz.QuizzActivity;
import com.google.firebase.database.ChildEventListener;
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
        executorService.execute(notify);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Database.getInstance();
        notify = new Notify();
        executorService = Executors.newFixedThreadPool(1);
        isFirsTimeRunning = true;
        stop = false;
    }

    private void sendMessage() {
        while (isDatabaseNull()) {
        }

        LocalDatabase.getInstance().updataData();

        Intent intent = new Intent("UpdateData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        isFirsTimeRunning = false;
    }


    class Notify implements Runnable {

        @Override
        public void run() {
            databaseValueEventListener();
            quizzChildEventListener();
        }
    }

    private void quizzChildEventListener() {
        Database.getInstance().getQuizzesReferece().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!isFirsTimeRunning) {
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

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
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
                    sendMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
