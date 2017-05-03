package com.endava.androidamweek.ui.speaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.model.Training;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SpeakerTrainingsAdapter extends RecyclerView.Adapter<SpeakerTrainingsAdapter.ViewHolder> {

    private List<Training> speakerTrainings;

    void updateList(List<Training> trainings) {
        speakerTrainings = trainings;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.speakerTrainingTitle)
        TextView speakerTrainingTitle;

        @BindView(R.id.speakerTrainingShortDescription)
        TextView speakerTrainingShortDescription;

        @BindView(R.id.speakerTrainingTime)
        TextView speakerTrainingTime;

        @BindView(R.id.speakerTrainingDate)
        TextView speakerTrainingDate;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    SpeakerTrainingsAdapter() {
        this.speakerTrainings = new ArrayList<>();
    }

    @Override
    public SpeakerTrainingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.speaker_item_training, parent, false);
        return new SpeakerTrainingsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SpeakerTrainingsAdapter.ViewHolder holder, int position) {
        Training item = speakerTrainings.get(position);
        holder.speakerTrainingTitle.setText(item.getTitle());
        holder.speakerTrainingShortDescription.setText(item.getStream());
        holder.speakerTrainingTime.setText(item.getTimeStart());
        holder.speakerTrainingDate.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return speakerTrainings.size();
    }

}
