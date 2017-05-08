package com.endava.androidamweek.data.callbacks;

import com.endava.androidamweek.data.model.Speaker;

import java.util.List;

public interface SpeakerCallback {

    void onSuccessResponse(List<Speaker> speakers);

    void onErrorResponse(String errorMessage);

}
