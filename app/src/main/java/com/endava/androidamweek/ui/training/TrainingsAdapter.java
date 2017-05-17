package com.endava.androidamweek.ui.training;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.main.SignInActivity;
import com.endava.androidamweek.ui.speaker.SpeakerClickListener;
import com.endava.androidamweek.utils.Utils;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private List<Training> trainingListForCurrentDay;
    private Boolean flag;
    private SpeakerClickListener speakerClickListener;
    private Utils utils;
    private int dayOfWeek;
    private Activity context;
    private SharedPreferences sharedPreferences;
    private final String ACCOUNT_PREFERENCES = "accountPreferences";
    private final static String USER_ID = "userID";
    private String userID;


    void updateList(int dayOfWeek) {
        userID = sharedPreferences.getString(USER_ID, "");

        this.dayOfWeek = dayOfWeek;
        this.trainingListForCurrentDay = utils.getCurrentDayTrainings(dayOfWeek);

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.foldingCell)
        FoldingCell foldingCell;

        @BindView(R.id.foldTrainingTitle)
        TextView foldTrainingTitle;

        @BindView(R.id.stream)
        TextView foldShortDescription;

        @BindView(R.id.foldTrainingTime)
        TextView foldTrainingTime;

        @BindView(R.id.star)
        ImageView starImage;

        @BindView(R.id.foldSpeakerImage)
        ImageView foldSpeakerImage;

        @BindView(R.id.unfoldSpeakerPhoto)
        ImageView unfoldSpeakerPhoto;

        @BindView(R.id.foldTrainingSpeaker)
        TextView foldTrainingSpeaker;

        @BindView(R.id.unfoldTrainingTitle)
        TextView unfoldTrainingTitle;

        @BindView(R.id.unfoldDate)
        TextView unfoldDate;

        @BindView(R.id.unfoldTrainingLocation)
        TextView unfoldTrainingLocation;

        @BindView(R.id.unfoldTrainingTime)
        TextView unfoldTrainingTime;

        @BindView(R.id.unfoldTrainingDescription)
        TextView unfoldTrainingDescription;

        @BindView(R.id.unfoldSpeakerName)
        TextView unfoldSpeakerName;

        @BindView(R.id.speakerLayout)
        LinearLayout speakerLayout;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foldingCell.toggle(false);
                }
            });
        }
    }

    TrainingsAdapter(Activity context) {
        this.context = context;
        LocalDatabase.getInstance().readFromDB();
        trainingListForCurrentDay = new ArrayList<>();
        utils = new Utils();

        sharedPreferences = context.getSharedPreferences(ACCOUNT_PREFERENCES, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USER_ID, "");


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Collections.sort(trainingListForCurrentDay);
        Training item = trainingListForCurrentDay.get(position);
        bindFoldView(holder, item);
        bindUnfoldView(holder, item);
    }

    private void bindFoldView(final ViewHolder holder, final Training item) {

        Picasso.with(context)
                .load(utils.getSpeakerImage(item.getSpeakerId()))
                .resize(100, 100)
                .into(holder.foldSpeakerImage);

        holder.foldTrainingTitle.setText(item.getTitle());
        holder.foldShortDescription.setText(item.getStream());
        holder.foldTrainingTime.setText(item.getTimeStart());
        holder.foldTrainingSpeaker.setText(utils.getSpeakerName(item.getSpeakerId()));

        if (!userID.equals("")) {
            if (utils.userHasCurrentTraining(userID, item)) {
                holder.starImage.setImageResource(R.drawable.ic_fill_star);
            }

        }

        holder.starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userID.equals("")) {
                    context.startActivity(new Intent(context, SignInActivity.class));
                    return;
                }

                if (!userID.equals("")) {
                    flag = utils.userHasCurrentTraining(userID, item);
                    flag = !flag;

                    if (flag) {
                        try {
                            sendNotification(item);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        utils.addTrainingToUser(userID, item);
                        holder.starImage.setImageResource(R.drawable.ic_fill_star);
                        Toast.makeText(context, "Training has added to your list", Toast.LENGTH_SHORT).show();
                    } else {

                        utils.removeTrainingToUser(userID, item);
                        holder.starImage.setImageResource(R.drawable.ic_star);
                        Toast.makeText(context, "Training has removed from your list", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
//////////////dddddddddddddddddddddddddddddddddddddd

    private void sendNotification(Training item) throws ParseException {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String currentTime = timeFormat.format(calendar.getTime());
        String currentDate = dateFormat.format(calendar.getTime());

        String trainingTime = item.getTimeStart();
        String trainingDate = item.getDate();


        String currentDay = currentDate + " " + currentTime;
        String trainingDay = trainingDate + " " + trainingTime;

        boolean isActualDate = checkDates(currentDay, trainingDay);

        Log.i(TAG, "isActualDate => " + isActualDate);

        if (!isActualDate) {
            scheduleNotification(getNotification("Too late, bro"), 1000);
        } else {

            long timeDifference = getTimeDifference(trainingDay, currentDay);
            long delay = getDelay(timeDifference);

            Log.i(TAG, "DELAY IN MILLIS => " + delay);
            Log.i(TAG, "DELAY IN HOURS => " + TimeUnit.HOURS.convert(delay, TimeUnit.MILLISECONDS));

            scheduleNotification(getNotification("Hey, hi, hello! Training speaker is waiting for you"), delay);
        }

        Log.i(TAG, "1 Training time => " + trainingTime + " " + "Training day => " + trainingDate);
        Log.i(TAG, "2 Current time => " + currentTime + " " + "Current day => " + currentDate);

    }


    private long getDelay(long timeDifference) throws ParseException {

        long millis = timeDifference - TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES);
        long delay;

        if (millis <= 0) {
            delay = 1000;
        } else {
            delay = millis;
        }

        return delay;
    }


    private long getTimeDifference(String trainingTime, String currentTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Date dateTraining = dateFormat.parse(trainingTime);
        Date dateCurrent = dateFormat.parse(currentTime);

        long mills = dateTraining.getTime() - dateCurrent.getTime();

        Log.i(TAG, "DIFFERENCE IN MINUTES => " + TimeUnit.MINUTES.convert(mills, TimeUnit.MILLISECONDS));

        return mills;
    }


    public static boolean checkDates(String currentDate, String trainingDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        boolean isActualDate = false;

        try {
            if (dateFormat.parse(currentDate).before(dateFormat.parse(trainingDate))) {
                isActualDate = true;  // if currentDate is before trainingDate
            } else if (dateFormat.parse(currentDate).equals(dateFormat.parse(trainingDate))) {
                isActualDate = true;  // if two dates are equal
            } else {
                isActualDate = false; // if currentDate is after the trainingDate
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isActualDate;
    }


    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Training Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_fill_star);
        return builder.build();
    }


    //ddddddddddddddddddddddddddd

    private void bindUnfoldView(ViewHolder holder, final Training item) {

        Picasso.with(context)
                .load(utils.getSpeakerImage(item.getSpeakerId()))
                .resize(100, 100)
                .into(holder.unfoldSpeakerPhoto);

        holder.unfoldTrainingTitle.setText(item.getTitle());
        holder.unfoldDate.setText(item.getDate());
        holder.unfoldTrainingLocation.setText(item.getLocation());
        holder.unfoldTrainingTime.setText(item.getTimeStart());
        holder.unfoldTrainingDescription.setText(item.getDescription());
        holder.unfoldSpeakerName.setText(utils.getSpeakerName(item.getSpeakerId()));

        holder.speakerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speakerId = utils.getSpeaker(item.getSpeakerId()).getId();
                speakerClickListener.onSpeakerClick(utils.getSpeaker(item.getSpeakerId()),
                        utils.getSpeakerTrainings(speakerId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainingListForCurrentDay.size();
    }

    void setOnSpeakerClickListener(SpeakerClickListener speakerClickListener) {
        this.speakerClickListener = speakerClickListener;
    }

}