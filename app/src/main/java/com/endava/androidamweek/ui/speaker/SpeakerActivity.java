package com.endava.androidamweek.ui.speaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.ui.main.BaseActivity;
import com.endava.androidamweek.ui.training.TrainingsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeakerActivity extends BaseActivity {

    @BindView(R.id.speaker_name)
    TextView speakerName;

    @BindView(R.id.speaker_photo)
    ImageView speakerPhoto;

    @BindView(R.id.speaker_short_info)
    TextView speakerShortInfo;

    @BindView(R.id.speaker_description)
    TextView speakerDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.speaker_trainings)
    RecyclerView speakerTrainings;

    SpeakerTrainingsAdapter adapter;
    private Speaker speaker;
    private List<Training> trainings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.toolbar_title_speaker);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        speaker = (Speaker) getIntent().getSerializableExtra(TrainingsFragment.SPEAKER);
        trainings = (List<Training>) getIntent().getSerializableExtra(TrainingsFragment.TRAINING_LIST);

        speakerTrainings.setLayoutManager(new LinearLayoutManager(this));
        speakerTrainings.setItemAnimator(new DefaultItemAnimator());

        adapter = new SpeakerTrainingsAdapter();
        speakerTrainings.setAdapter(adapter);

        speakerName.setText(speaker.getName());
        speakerDescription.setText(speaker.getLongInfo());

        Picasso.with(getBaseContext())
                .load(speaker.getImageId())
                .resize(1000, 1000)
                .into(speakerPhoto);

        adapter.updateList(trainings);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.speaker_profile;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
