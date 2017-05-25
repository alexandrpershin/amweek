package com.endava.androidamweek.ui.training;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.ViewHolder> {

    private List<Training> trainingListForCurrentDay;
    private Boolean flag;
    private SpeakerClickListener speakerClickListener;
    private Utils utils;
    private final String ACCOUNT_PREFERENCES = "accountPreferences";
    private final static String USER_ID = "userID";
    SharedPreferences sharedPreferences;
    private int dayOfWeek;
    private Context context;
    private String userID;
    private TrainingNotification notification;


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

        @BindView(R.id.unfoldLanguage)
        TextView  unfoldLanguage;

        @BindView(R.id.streamTitle)
        TextView  streamTitle;

        @BindView(R.id.unfoldType)
        TextView  unfoldType;


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

    TrainingsAdapter(Context context ) {
        this.context = context;
        LocalDatabase.getInstance().readFromDB();
        trainingListForCurrentDay = new ArrayList<>();
        utils = new Utils();

        notification = new TrainingNotification(context);

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
        holder.foldTrainingTime.setText(item.getTimeStart()+"-"+item.getTimeEnd());
        holder.foldTrainingSpeaker.setText(utils.getSpeakerName(item.getSpeakerId()));
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
                            notification.sendNotification(item);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        utils.addTrainingToUser(userID, item);
                        holder.starImage.setImageResource(R.drawable.ic_fill_star);
                        Toast.makeText(v.getContext(), "You've setup a remainder", Toast.LENGTH_SHORT).show();
                    } else {
                        notification.cancel(item.getId());
                        utils.removeTrainingToUser(userID, item);
                        holder.starImage.setImageResource(R.drawable.ic_star);
                        Toast.makeText(v.getContext(), "You've deleted the remainder", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        if (!userID.equals("")) {
            if (utils.userHasCurrentTraining(userID, item)) {
                holder.starImage.setImageResource(R.drawable.ic_fill_star);
            }

        }

    }


    private void bindUnfoldView(ViewHolder holder, final Training item) {

        Picasso.with(context)
                .load(utils.getSpeakerImage(item.getSpeakerId()))
                .resize(100, 100)
                .into(holder.unfoldSpeakerPhoto);

        holder.unfoldTrainingTitle.setText(item.getTitle());
        holder.unfoldDate.setText(item.getDate());
        holder.unfoldTrainingLocation.setText(item.getLocation());
        holder.unfoldTrainingTime.setText(item.getTimeStart()+"-"+item.getTimeEnd());
        holder.unfoldTrainingDescription.setText(item.getDescription());
        holder.unfoldSpeakerName.setText(utils.getSpeakerName(item.getSpeakerId()));
        holder.unfoldLanguage.setText(item.getLanguage());
        holder.streamTitle.setText(item.getStream());
        holder.unfoldType.setText(item.getType());

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