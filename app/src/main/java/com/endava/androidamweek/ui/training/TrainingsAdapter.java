package com.endava.androidamweek.ui.training;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.speaker.SpeakerClickListener;
import com.endava.androidamweek.utils.Utils;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.ViewHolder> {

    private List<Training> trainingListForCurrentDay;

    private SpeakerClickListener speakerClickListener;
    private Utils utils;
    private int dayOfWeek;
    private Context context;


    void updateList( int dayOfWeek) {
        this.dayOfWeek=dayOfWeek;
        this.trainingListForCurrentDay = utils.getCurrentDayTrainings( dayOfWeek);

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

    TrainingsAdapter(Context context) {
        this.context=context;

        trainingListForCurrentDay = new ArrayList<>();
        utils = new Utils();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Training item = trainingListForCurrentDay.get(position);
        bindFoldView(holder, item);
        bindUnfoldView(holder, item);
    }

    private void bindFoldView(ViewHolder holder, Training item) {

        Picasso.with(context)
                .load(utils.getSpeakerImage(item.getSpeakerId()))
                .resize(100, 100)
                .into(holder.foldSpeakerImage);

        holder.foldTrainingTitle.setText(item.getTitle());
        holder.foldShortDescription.setText(item.getStream());
        holder.foldTrainingTime.setText(item.getTimeStart());
        holder.foldTrainingSpeaker.setText(utils.getSpeakerName(item.getSpeakerId()));
    }


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
        holder.unfoldSpeakerName.setText(utils.getSpeakerName( item.getSpeakerId()));

        holder.speakerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speakerId = utils.getSpeaker( item.getSpeakerId()).getId();
                speakerClickListener.onSpeakerClick(utils.getSpeaker( item.getSpeakerId()),
                        utils.getSpeakerTrainings( speakerId));
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