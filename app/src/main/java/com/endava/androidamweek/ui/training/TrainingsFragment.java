package com.endava.androidamweek.ui.training;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.speaker.SpeakerActivity;
import com.endava.androidamweek.ui.speaker.SpeakerClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingsFragment extends Fragment implements SpeakerClickListener {

    public static final String SPEAKER = "speaker";
    public static final String TRAINING_LIST = "trainingList";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    TrainingsAdapter adapter;
    private final static String DAY_OF_WEEK = "dayOfWeek";
    private static final String ADAPTER_POSITION = "adapterPosition";
    private List<Speaker> speakers;
    private List<Training> trainings;
    private int dayOfWeek;
    private int adapterPosition;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        ButterKnife.bind(this, view);

        LocalDatabase.getInstance().setContext(getContext());

        bundle = getArguments();
        dayOfWeek = bundle.getInt(DAY_OF_WEEK);
        adapterPosition = bundle.getInt(ADAPTER_POSITION);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    @Override
    public void onSpeakerClick(Speaker speaker, List<Training> trainings) {
        Intent intent = new Intent(getActivity(), SpeakerActivity.class);
        intent.putExtra(SPEAKER, speaker);
        intent.putExtra(TRAINING_LIST, (Serializable) trainings);
        startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.updateList(dayOfWeek);
        }
    };


    @Override
    public void onResume() {
        super.onResume();

        adapter = new TrainingsAdapter(getActivity(), adapterPosition);

        adapter.setOnSpeakerClickListener(this);

        if (!isDatabaseNull())
            adapter.updateList(dayOfWeek);

        recyclerView.setAdapter(adapter);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("UpdateData"));
    }

    public boolean isDatabaseNull() {
        return (LocalDatabase.getInstance().getSpeakers() == null || LocalDatabase.getInstance().getTrainings() == null
                || LocalDatabase.getInstance().getUsers() == null || LocalDatabase.getInstance().getQuizzes() == null);
    }


}
