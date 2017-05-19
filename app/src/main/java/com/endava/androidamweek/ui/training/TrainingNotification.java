package com.endava.androidamweek.ui.training;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.main.ScheduleActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TrainingNotification {
    private static final String TAG = "TAG";
    private Context context;
    private AlarmManager alarmManager;
    public Training item;
    private final int OVERDUE = 0;
    private final int IS_GOING = 1;
    private final int WILL_GOING = 2;

    public TrainingNotification(Context context) {
        this.context = context;
    }

    public void sendNotification(Training item) throws ParseException {
        this.item=item;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String currentTime = timeFormat.format(calendar.getTime());
        String currentDate = dateFormat.format(calendar.getTime());

        String trainingTimeStart = item.getTimeStart();
        String trainingTimeEnd = item.getTimeEnd();
        String trainingDate = item.getDate();


        String currentDay = currentDate + " " + currentTime;
        String trainingDayStartTime = trainingDate + " " + trainingTimeStart;
        String trainingDayEndTime = trainingDate + " " + trainingTimeEnd;

        int trainingState = checkState(currentDay, trainingDayStartTime, trainingDayEndTime);

        switch (trainingState) {
            case OVERDUE:
                scheduleNotification(item.getId(), getNotification(item.getTitle(), "has passed"), 1000);
                break;
            case IS_GOING:
                scheduleNotification(item.getId(), getNotification(item.getTitle(), "Currently active, started at:" + item.getTimeStart()), 1000);
                break;
            case WILL_GOING:

                long minutes = calculateMinutesUntilTraining(currentDay, trainingDayStartTime);
                if (minutes <= 15) {
                    scheduleNotification(item.getId(), getNotification(item.getTitle(), "Trainig will start after " + minutes + " minutes"), 1000);
                } else {
                    scheduleNotification(item.getId(), getNotification(item.getTitle(), "Trainig will start after 15 minutes"), (minutes - 15) * 60000);
                }

                break;
        }

    }

    private long calculateMinutesUntilTraining(String currentDay, String trainingDayStartTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        long minutes = ((dateFormat.parse(trainingDayStartTime).getTime()) / (60000)) - ((dateFormat.parse(currentDay).getTime()) / (60000));

        return minutes;
    }

    public int checkState(String currentDate, String trainingDateStartTime, String trainingDateEndTime) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {

            if (dateFormat.parse(currentDate).after(dateFormat.parse(trainingDateEndTime))) {
                return 0;
            }

            if (dateFormat.parse(currentDate).before(dateFormat.parse(trainingDateEndTime)) &&
                    dateFormat.parse(currentDate).after(dateFormat.parse(trainingDateStartTime))) {
                return 1;
            }

            if (dateFormat.parse(currentDate).before(dateFormat.parse(trainingDateStartTime))) {
                return 2;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void scheduleNotification(int trainingId, Notification notification, long delay) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, trainingId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.i("PENDINGINTENTNOTIFTION",trainingId+" created ");

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content, String time) {

        Intent startActivity = new Intent(context, ScheduleActivity.class);
        startActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,startActivity,PendingIntent.FLAG_UPDATE_CURRENT);


        Notification builder = new Notification.Builder(context)
                .setStyle(new Notification.InboxStyle()

                        .setBigContentTitle(content)
                        .addLine(time)
                        .setSummaryText("Training Notification"))
                .setSmallIcon(R.drawable.ic_fill_star)
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .build();


        return builder;
    }

    public void cancel(int trainingId) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, trainingId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) (context.getSystemService(context.ALARM_SERVICE));
        Log.i("PENDINGINTENTNOTIFTION",trainingId+" canceled");
        alarmManager.cancel(pendingIntent);
    }
}
