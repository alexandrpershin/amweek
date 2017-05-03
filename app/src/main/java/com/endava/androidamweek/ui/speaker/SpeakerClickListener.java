package com.endava.androidamweek.ui.speaker;

import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;

import java.util.List;

public interface SpeakerClickListener {
    void onSpeakerClick( Speaker speaker, List<Training> trainings);
}
