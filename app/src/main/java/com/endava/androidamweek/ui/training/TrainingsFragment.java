package com.endava.androidamweek.ui.training;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.callbacks.TrainingCallback;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.speaker.SpeakerActivity;
import com.endava.androidamweek.ui.speaker.SpeakerClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingsFragment extends Fragment implements TrainingCallback, SpeakerClickListener {

    public static final String SPEAKER = "speaker";
    public static final String TRAINING_LIST = "trainingList";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    TrainingsAdapter adapter;
    private final static String DAY_OF_WEEK = "dayOfWeek";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        ButterKnife.bind(this, view);

        TrainingsPresenter trainingsPresenter = new TrainingsPresenter();
        trainingsPresenter.loadTrainings(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new TrainingsAdapter();

        adapter.setOnSpeakerClickListener(this);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSuccessResponse(List<Training> trainings, List<Speaker> speakers) {
        Bundle bundle = getArguments();
        int dayOfWeek = bundle.getInt(DAY_OF_WEEK);
        adapter.updateList(trainings, speakers, dayOfWeek);
    }

    @Override
    public void onErrorResponse(String errorMessage) {

    }

    @Override
    public void onSpeakerClick(Speaker speaker, List<Training> trainings) {
        Intent intent = new Intent(getActivity(), SpeakerActivity.class);
        intent.putExtra(SPEAKER, speaker);
        intent.putExtra(TRAINING_LIST, (Serializable) trainings);
        startActivity(intent);
    }

}
